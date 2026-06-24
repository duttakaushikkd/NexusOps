package com.nexusops.agents.scrumagent.model;

import com.nexusops.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "stories")
public class Story extends BaseEntity {
    private UUID sprintId;
    private UUID projectId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String status = "BACKLOG";
    private Integer storyPoints = 0;
    private String priority = "MEDIUM";
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
