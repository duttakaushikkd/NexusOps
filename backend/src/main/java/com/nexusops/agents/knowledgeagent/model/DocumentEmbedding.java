package com.nexusops.agents.knowledgeagent.model;

import com.nexusops.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "embeddings")
public class DocumentEmbedding extends BaseEntity {
    private UUID documentId;
    private Integer chunkIndex;
    @Column(columnDefinition = "TEXT")
    private String content;
    @JdbcTypeCode(SqlTypes.OTHER)
    @Column(columnDefinition = "vector(1536)")
    private String embedding;
    private Instant createdAt = Instant.now();
}
