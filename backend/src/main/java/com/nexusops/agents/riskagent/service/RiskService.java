package com.nexusops.agents.riskagent.service;

import com.nexusops.agents.riskagent.dto.RiskDtos;
import com.nexusops.ai.AiService;
import com.nexusops.ai.PromptService;
import com.nexusops.events.RiskDetectedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskService {
    private final AiService aiService;
    private final PromptService promptService;
    private final ApplicationEventPublisher events;

    public RiskDtos.RiskResponse analyze(RiskDtos.RiskRequest request) {
        var riskScore = heuristicScore(request.scope() + " " + request.context());
        var prompt = "Scope: " + request.scope() + "\nContext: " + request.context() + "\nRisk score heuristic: " + riskScore;
        String report;
        try {
            report = aiService.complete(promptService.getPrompt("risk-agent-prompt"), prompt);
        } catch (Exception ex) {
            report = "Risk score " + riskScore + ". Watch schedule variance, unresolved dependencies, overloaded owners, and release readiness gaps.";
        }
        if (riskScore >= 70) {
            events.publishEvent(new RiskDetectedEvent(request.scope(), riskScore));
        }
        return new RiskDtos.RiskResponse(riskScore, 0.78, report, List.of("Assign explicit owners", "Review dependencies daily", "Reduce WIP", "Add rollback and contingency plan"));
    }

    private int heuristicScore(String text) {
        var normalized = text == null ? "" : text.toLowerCase();
        var score = 25;
        for (var keyword : List.of("delay", "blocked", "critical", "dependency", "overload", "risk", "failed", "urgent")) {
            if (normalized.contains(keyword)) {
                score += 9;
            }
        }
        return Math.min(100, score);
    }
}
