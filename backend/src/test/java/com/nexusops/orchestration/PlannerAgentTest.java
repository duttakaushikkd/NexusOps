package com.nexusops.orchestration;

import com.nexusops.ai.AiService;
import com.nexusops.ai.PromptService;
import com.nexusops.planner.PlannerAgent;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlannerAgentTest {
    @Test
    void selectsReleaseAndScrumAgents() {
        var ai = mock(AiService.class);
        var prompts = mock(PromptService.class);
        when(prompts.getPrompt("planner-agent-prompt")).thenReturn("prompt");
        when(ai.complete("prompt", "Create release plan for sprint Alpha")).thenReturn("{}");
        var planner = new PlannerAgent(ai, prompts);

        var response = planner.execute(new AgentRequest(UUID.randomUUID(), "Create release plan for sprint Alpha", Map.of()));

        assertThat(response.data().get("agents").toString()).contains("SCRUM_AGENT", "RELEASE_AGENT");
    }
}
