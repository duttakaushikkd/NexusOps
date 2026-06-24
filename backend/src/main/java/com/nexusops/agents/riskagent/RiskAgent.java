package com.nexusops.agents.riskagent;

import com.nexusops.agents.riskagent.dto.RiskDtos;
import com.nexusops.agents.riskagent.service.RiskService;
import com.nexusops.orchestration.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RiskAgent implements Agent {
    private final RiskService riskService;

    @Override
    public AgentResponse execute(AgentRequest request) {
        var result = riskService.analyze(new RiskDtos.RiskRequest(request.userPrompt(), String.valueOf(request.context())));
        return new AgentResponse(type(), result.report(), Map.of("riskScore", result.riskScore(), "confidenceScore", result.confidenceScore()), 1, 1);
    }

    @Override
    public AgentType type() {
        return AgentType.RISK_AGENT;
    }
}
