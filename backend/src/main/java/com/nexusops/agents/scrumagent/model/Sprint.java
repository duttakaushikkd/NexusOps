package com.nexusops.agents.scrumagent.model;

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
@Table(name = "sprints")
public class Sprint extends BaseEntity {
    @Column(name = "project_id")
    private UUID projectId;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String goal;
    private String status = "PLANNED";
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer velocity = 0;
    private Integer capacity = 0;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
