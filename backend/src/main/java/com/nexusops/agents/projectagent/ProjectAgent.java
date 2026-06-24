package com.nexusops.agents.projectagent;

import com.nexusops.ai.AiService;
import com.nexusops.ai.PromptService;
import com.nexusops.orchestration.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProjectAgent implements Agent {
    private final AiService aiService;
    private final PromptService promptService;

    @Override
    public AgentResponse execute(AgentRequest request) {
        var content = safeComplete("project-agent-prompt", request.userPrompt());
        return new AgentResponse(type(), content, Map.of("capability", "project-management"), 1, 0);
    }

    @Override
    public AgentType type() {
        return AgentType.PROJECT_AGENT;
    }

    private String safeComplete(String promptName, String userPrompt) {
        try {
            return aiService.complete(promptService.getPrompt(promptName), userPrompt);
        } catch (Exception ex) {
            return "Project plan draft: define scope, milestones, team roles, delivery risks, and measurable health indicators for: " + userPrompt;
        }
    }
}
