package com.nexusops.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/ai")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminAiController {
    private final AiService aiService;
    private final PromptService promptService;

    @GetMapping("/models")
    Map<String, Object> models() {
        return Map.of("current", aiService.currentModel(), "supported", aiService.supportedModels());
    }

    @PutMapping("/models")
    Map<String, String> switchModel(@RequestBody Map<String, String> request) {
        aiService.switchModel(request.get("model"));
        return Map.of("current", aiService.currentModel());
    }

    @GetMapping("/prompts")
    List<PromptTemplate> prompts() {
        return promptService.list();
    }

    @PutMapping("/prompts/{name}")
    PromptTemplate savePrompt(@PathVariable String name, @RequestBody Map<String, String> request) {
        return promptService.save(name, request.get("content"));
    }
}
