package com.nexusops.agents.projectagent.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

public final class ProjectDtos {
    private ProjectDtos() {}

    public record ProjectRequest(@NotBlank String name, String description, String owner, String status) {}
    public record ProjectResponse(UUID id, String name, String description, String owner, String status, Integer healthScore, Instant createdAt, Instant updatedAt) {}
}
