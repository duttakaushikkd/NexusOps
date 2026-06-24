package com.nexusops.ai;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "system_settings")
public class SystemSetting {
    @Id
    @Column(name = "key")
    private String key;
    @Column(columnDefinition = "TEXT")
    private String value;
    private Instant updatedAt = Instant.now();
}
