package com.nexusops.agents.releaseagent;

import com.nexusops.ai.AiService;
import com.nexusops.ai.PromptService;
import com.nexusops.orchestration.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReleaseAgent implements Agent {
    private final AiService aiService;
    private final PromptService promptService;

    @Override
    public AgentResponse execute(AgentRequest request) {
        var content = safeComplete(request.userPrompt());
        return new AgentResponse(type(), content, Map.of("capability", "release-planning"), 1, 0);
    }

    @Override
    public AgentType type() {
        return AgentType.RELEASE_AGENT;
    }

    private String safeComplete(String userPrompt) {
        try {
            return aiService.complete(promptService.getPrompt("release-agent-prompt"), userPrompt);
        } catch (Exception ex) {
            return "Release plan draft: scope, readiness gates, deployment checklist, release notes, rollback plan, and owner map for: " + userPrompt;
        }
    }
}
