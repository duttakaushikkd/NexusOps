package com.nexusops.agents.releaseagent.model;

import com.nexusops.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "releases")
public class ProjectRelease extends BaseEntity {
    private UUID projectId;
    private String version;
    private String name;
    private String status = "PLANNED";
    private LocalDate releaseDate;
    private Integer readinessScore = 0;
    @Column(columnDefinition = "TEXT")
    private String notes;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
