package com.nexusops.planner;

import com.nexusops.ai.AiService;
import com.nexusops.ai.PromptService;
import com.nexusops.orchestration.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PlannerAgent implements Agent {
    private final AiService aiService;
    private final PromptService promptService;

    @Override
    public AgentResponse execute(AgentRequest request) {
        var selected = selectAgents(request.userPrompt());
        var workflow = selected.stream().map(type -> "Execute " + type.name()).toList();
        String aiPlan;
        try {
            aiPlan = aiService.complete(promptService.getPrompt("planner-agent-prompt"), request.userPrompt());
        } catch (Exception ex) {
            aiPlan = "{\"workflow\":" + workflow + ",\"agents\":" + selected + "}";
        }
        return new AgentResponse(type(), aiPlan, Map.of("agents", selected, "workflow", workflow), 1, 0);
    }

    @Override
    public AgentType type() {
        return AgentType.PLANNER_AGENT;
    }

    private List<AgentType> selectAgents(String prompt) {
        var p = prompt.toLowerCase(Locale.ROOT);
        var map = new LinkedHashMap<AgentType, Boolean>();
        map.put(AgentType.PROJECT_AGENT, p.contains("project") || p.contains("milestone") || p.contains("team"));
        map.put(AgentType.SCRUM_AGENT, p.contains("sprint") || p.contains("scrum") || p.contains("story") || p.contains("backlog"));
        map.put(AgentType.RELEASE_AGENT, p.contains("release") || p.contains("deploy"));
        map.put(AgentType.KNOWLEDGE_AGENT, p.contains("document") || p.contains("knowledge") || p.contains("search"));
        map.put(AgentType.RISK_AGENT, p.contains("risk") || p.contains("delay") || p.contains("health"));
        var selected = map.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).toList();
        return selected.isEmpty() ? List.of(AgentType.PROJECT_AGENT, AgentType.RISK_AGENT) : selected;
    }
}
