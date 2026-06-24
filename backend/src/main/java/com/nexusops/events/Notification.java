package com.nexusops.events;

import com.nexusops.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {
    private UUID userId;
    private String type;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String message;
    @Column(name = "read")
    private boolean read;
    private Instant createdAt = Instant.now();
}
