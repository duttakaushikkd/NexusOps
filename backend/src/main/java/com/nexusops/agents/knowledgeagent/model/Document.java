package com.nexusops.agents.knowledgeagent.model;

import com.nexusops.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "documents")
public class Document extends BaseEntity {
    private UUID projectId;
    private String title;
    private String sourceType;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String status = "PROCESSED";
    private Instant createdAt = Instant.now();
}
