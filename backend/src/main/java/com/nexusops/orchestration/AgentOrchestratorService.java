package com.nexusops.orchestration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexusops.planner.PlannerAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AgentOrchestratorService {
    private final AgentRegistry registry;
    private final PlannerAgent plannerAgent;
    private final AgentExecutionHistoryRepository historyRepository;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public OrchestrationResponse execute(String prompt) {
        var correlationId = UUID.randomUUID();
        var context = new LinkedHashMap<String, Object>();
        var initialRequest = new AgentRequest(correlationId, prompt, context);
        var plan = executeAgent(plannerAgent, initialRequest);
        @SuppressWarnings("unchecked")
        var agents = (List<AgentType>) plan.data().getOrDefault("agents", List.of(AgentType.PROJECT_AGENT));
        var responses = new ArrayList<AgentResponse>();
        responses.add(plan);
        for (var agentType : agents) {
            var response = executeAgent(registry.get(agentType), new AgentRequest(correlationId, prompt, context));
            context.put(agentType.name(), response.content());
            responses.add(response);
        }
        cacheContext(correlationId, context);
        return new OrchestrationResponse(correlationId, responses, aggregate(responses));
    }

    private AgentResponse executeAgent(Agent agent, AgentRequest request) {
        var start = Instant.now();
        var history = new AgentExecutionHistory();
        history.setCorrelationId(request.correlationId());
        history.setAgentType(agent.type());
        history.setRequest(request.userPrompt());
        try {
            var response = agent.execute(request);
            history.setStatus("SUCCESS");
            history.setResponse(response.content());
            history.setLlmCalls(response.llmCalls());
            history.setToolCalls(response.toolCalls());
            return response;
        } catch (Exception ex) {
            history.setStatus("FAILED");
            history.setErrorMessage(ex.getMessage());
            throw ex;
        } finally {
            history.setExecutionTimeMs(Duration.between(start, Instant.now()).toMillis());
            historyRepository.save(history);
        }
    }

    private void cacheContext(UUID correlationId, Map<String, Object> context) {
        try {
            redisTemplate.opsForValue().set("agent-context:" + correlationId, objectMapper.writeValueAsString(context), Duration.ofHours(12));
        } catch (Exception ignored) {
        }
    }

    private String aggregate(List<AgentResponse> responses) {
        var builder = new StringBuilder();
        responses.forEach(response -> builder.append("## ").append(response.agentType()).append("\n").append(response.content()).append("\n\n"));
        return builder.toString();
    }

    public record OrchestrationResponse(UUID correlationId, List<AgentResponse> responses, String answer) {}
}
