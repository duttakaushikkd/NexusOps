package com.nexusops.orchestration;

import com.nexusops.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "agent_execution_history")
public class AgentExecutionHistory extends BaseEntity {
    private UUID correlationId;
    @Enumerated(EnumType.STRING)
    private AgentType agentType;
    @Column(columnDefinition = "TEXT")
    private String request;
    @Column(columnDefinition = "TEXT")
    private String response;
    private String status;
    private Long executionTimeMs = 0L;
    private Integer llmCalls = 0;
    private Integer toolCalls = 0;
    @Column(columnDefinition = "TEXT")
    private String errorMessage;
    private Instant createdAt = Instant.now();
}
