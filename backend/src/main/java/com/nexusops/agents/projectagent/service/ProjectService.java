package com.nexusops.agents.projectagent.service;

import com.nexusops.agents.projectagent.dto.ProjectDtos;
import com.nexusops.agents.projectagent.mapper.ProjectMapper;
import com.nexusops.common.NotFoundException;
import com.nexusops.events.ProjectCreatedEvent;
import com.nexusops.shared.Project;
import com.nexusops.shared.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    private final ProjectMapper mapper;
    private final ApplicationEventPublisher events;

    public Page<ProjectDtos.ProjectResponse> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public ProjectDtos.ProjectResponse get(UUID id) {
        return mapper.toResponse(find(id));
    }

    @Transactional
    public ProjectDtos.ProjectResponse create(ProjectDtos.ProjectRequest request) {
        var project = new Project();
        project.setName(request.name());
        project.setDescription(request.description());
        project.setOwner(request.owner());
        if (request.status() != null) {
            project.setStatus(request.status());
        }
        var saved = repository.save(project);
        events.publishEvent(new ProjectCreatedEvent(saved.getId(), saved.getName()));
        return mapper.toResponse(saved);
    }

    @Transactional
    public ProjectDtos.ProjectResponse update(UUID id, ProjectDtos.ProjectRequest request) {
        var project = find(id);
        project.setName(request.name());
        project.setDescription(request.description());
        project.setOwner(request.owner());
        if (request.status() != null) {
            project.setStatus(request.status());
        }
        return mapper.toResponse(project);
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(find(id));
    }

    private Project find(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
    }
}
