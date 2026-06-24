package com.nexusops.agents.scrumagent;

import com.nexusops.ai.AiService;
import com.nexusops.ai.PromptService;
import com.nexusops.orchestration.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ScrumAgent implements Agent {
    private final AiService aiService;
    private final PromptService promptService;

    @Override
    public AgentResponse execute(AgentRequest request) {
        var content = safeComplete(request.userPrompt());
        return new AgentResponse(type(), content, Map.of("capability", "scrum-planning"), 1, 0);
    }

    @Override
    public AgentType type() {
        return AgentType.SCRUM_AGENT;
    }

    private String safeComplete(String userPrompt) {
        try {
            return aiService.complete(promptService.getPrompt("scrum-agent-prompt"), userPrompt);
        } catch (Exception ex) {
            return "Sprint plan draft: backlog items, story points, task breakdown, dependencies, sprint goal, and capacity assumptions for: " + userPrompt;
        }
    }
}
