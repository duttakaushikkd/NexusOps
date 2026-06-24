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
@Table(name = "tasks")
public class Task extends BaseEntity {
    private UUID storyId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String status = "TODO";
    private String assignee;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
