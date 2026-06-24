package com.nexusops.agents.riskagent.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public final class RiskDtos {
    private RiskDtos() {}
    public record RiskRequest(@NotBlank String scope, String context) {}
    public record RiskResponse(int riskScore, double confidenceScore, String report, List<String> mitigations) {}
}
