package com.nexusops.agents.scrumagent.repository;

import com.nexusops.agents.scrumagent.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoryRepository extends JpaRepository<Story, UUID> {
    List<Story> findBySprintId(UUID sprintId);
}
