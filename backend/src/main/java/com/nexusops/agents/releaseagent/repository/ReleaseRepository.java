package com.nexusops.agents.releaseagent.repository;

import com.nexusops.agents.releaseagent.model.ProjectRelease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReleaseRepository extends JpaRepository<ProjectRelease, UUID> {
    long countByStatus(String status);
}
