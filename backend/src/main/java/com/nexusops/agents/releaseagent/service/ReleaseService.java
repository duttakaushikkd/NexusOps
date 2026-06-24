package com.nexusops.agents.releaseagent.service;

import com.nexusops.agents.releaseagent.dto.ReleaseDtos;
import com.nexusops.agents.releaseagent.model.ProjectRelease;
import com.nexusops.agents.releaseagent.repository.ReleaseRepository;
import com.nexusops.events.ReleaseCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReleaseService {
    private final ReleaseRepository repository;
    private final ApplicationEventPublisher events;

    public Page<ReleaseDtos.ReleaseResponse> list(Pageable pageable) {
        return repository.findAll(pageable).map(this::map);
    }

    public ReleaseDtos.ReleaseResponse create(ReleaseDtos.ReleaseRequest request) {
        var release = new ProjectRelease();
        release.setProjectId(request.projectId());
        release.setVersion(request.version());
        release.setName(request.name());
        release.setReleaseDate(request.releaseDate());
        release.setNotes(request.notes());
        var saved = repository.save(release);
        events.publishEvent(new ReleaseCreatedEvent(saved.getId(), saved.getName()));
        return map(saved);
    }

    private ReleaseDtos.ReleaseResponse map(ProjectRelease release) {
        return new ReleaseDtos.ReleaseResponse(release.getId(), release.getProjectId(), release.getVersion(), release.getName(), release.getStatus(), release.getReleaseDate(), release.getReadinessScore(), release.getNotes());
    }
}
