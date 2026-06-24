package com.nexusops.agents.knowledgeagent.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public final class KnowledgeDtos {
    private KnowledgeDtos() {}
    public record DocumentRequest(UUID projectId, @NotBlank String title, @NotBlank String sourceType, @NotBlank String content) {}
    public record DocumentResponse(UUID id, String title, String sourceType, String status) {}
    public record SearchRequest(@NotBlank String query) {}
    public record SearchResponse(String answer, List<String> chunks) {}
}
