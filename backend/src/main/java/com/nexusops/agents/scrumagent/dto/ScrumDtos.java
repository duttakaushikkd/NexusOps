package com.nexusops.agents.scrumagent.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public final class ScrumDtos {
    private ScrumDtos() {}
    public record SprintRequest(UUID projectId, @NotBlank String name, String goal, LocalDate startDate, LocalDate endDate, Integer capacity) {}
    public record SprintResponse(UUID id, UUID projectId, String name, String goal, String status, LocalDate startDate, LocalDate endDate, Integer velocity, Integer capacity) {}
}
