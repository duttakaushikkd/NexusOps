package com.nexusops.orchestration;

import java.util.Map;

public record AgentResponse(
        AgentType agentType,
        String content,
        Map<String, Object> data,
        int llmCalls,
        int toolCalls
) {
}
