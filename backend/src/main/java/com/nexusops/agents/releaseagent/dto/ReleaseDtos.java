package com.nexusops.agents.releaseagent.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public final class ReleaseDtos {
    private ReleaseDtos() {}
    public record ReleaseRequest(UUID projectId, @NotBlank String version, @NotBlank String name, LocalDate releaseDate, String notes) {}
    public record ReleaseResponse(UUID id, UUID projectId, String version, String name, String status, LocalDate releaseDate, Integer readinessScore, String notes) {}
}
