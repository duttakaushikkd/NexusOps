package com.nexusops.shared;

import com.nexusops.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "projects")
@EntityListeners(AuditingEntityListener.class)
public class Project extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String owner;
    private String status = "ACTIVE";
    private Integer healthScore = 75;
    @Column(columnDefinition = "TEXT")
    private String description;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
