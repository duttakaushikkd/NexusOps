package com.nexusops.agents.scrumagent.repository;

import com.nexusops.agents.scrumagent.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SprintRepository extends JpaRepository<Sprint, UUID> {
    long countByStatus(String status);
}
