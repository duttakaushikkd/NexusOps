package com.nexusops.orchestration;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    private final AgentOrchestratorService orchestrator;
    private final AgentExecutionHistoryRepository historyRepository;

    @PostMapping("/chat")
    AgentOrchestratorService.OrchestrationResponse chat(@RequestBody Map<String, String> request) {
        return orchestrator.execute(request.getOrDefault("message", ""));
    }

    @GetMapping("/agents/history")
    List<AgentExecutionHistory> history() {
        return historyRepository.findTop50ByOrderByCreatedAtDesc();
    }
}
