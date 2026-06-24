package com.nexusops.agents.projectagent.mapper;

import com.nexusops.agents.projectagent.dto.ProjectDtos;
import com.nexusops.shared.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public ProjectDtos.ProjectResponse toResponse(Project project) {
        return new ProjectDtos.ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner(),
                project.getStatus(),
                project.getHealthScore(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
