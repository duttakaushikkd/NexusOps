package com.nexusops.agents.scrumagent.service;

import com.nexusops.agents.scrumagent.dto.ScrumDtos;
import com.nexusops.agents.scrumagent.model.Sprint;
import com.nexusops.agents.scrumagent.repository.SprintRepository;
import com.nexusops.events.SprintCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrumService {
    private final SprintRepository repository;
    private final ApplicationEventPublisher events;

    public Page<ScrumDtos.SprintResponse> list(Pageable pageable) {
        return repository.findAll(pageable).map(this::map);
    }

    public ScrumDtos.SprintResponse create(ScrumDtos.SprintRequest request) {
        var sprint = new Sprint();
        sprint.setProjectId(request.projectId());
        sprint.setName(request.name());
        sprint.setGoal(request.goal());
        sprint.setStartDate(request.startDate());
        sprint.setEndDate(request.endDate());
        sprint.setCapacity(request.capacity() == null ? 0 : request.capacity());
        var saved = repository.save(sprint);
        events.publishEvent(new SprintCreatedEvent(saved.getId(), saved.getName()));
        return map(saved);
    }

    private ScrumDtos.SprintResponse map(Sprint sprint) {
        return new ScrumDtos.SprintResponse(sprint.getId(), sprint.getProjectId(), sprint.getName(), sprint.getGoal(), sprint.getStatus(), sprint.getStartDate(), sprint.getEndDate(), sprint.getVelocity(), sprint.getCapacity());
    }
}
