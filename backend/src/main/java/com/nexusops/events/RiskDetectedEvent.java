package com.nexusops.events;

public record RiskDetectedEvent(String scope, int riskScore) {
}
