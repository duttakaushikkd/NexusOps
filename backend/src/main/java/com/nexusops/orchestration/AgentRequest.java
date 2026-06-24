package com.nexusops.orchestration;

import java.util.Map;
import java.util.UUID;

public record AgentRequest(
        UUID correlationId,
        String userPrompt,
        Map<String, Object> context
) {
}
