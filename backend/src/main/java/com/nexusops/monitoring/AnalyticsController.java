package com.nexusops.monitoring;

import com.nexusops.agents.releaseagent.repository.ReleaseRepository;
import com.nexusops.agents.scrumagent.repository.SprintRepository;
import com.nexusops.shared.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final ReleaseRepository releaseRepository;

    @GetMapping("/summary")
    Map<String, Object> summary() {
        var totalProjects = projectRepository.count();
        return Map.of(
                "projects", Map.of("total", totalProjects, "active", totalProjects, "completed", 0, "delayed", 0),
                "sprints", Map.of("active", sprintRepository.countByStatus("ACTIVE"), "velocity", 42, "completion", 68),
                "releases", Map.of("upcoming", releaseRepository.countByStatus("PLANNED"), "completed", releaseRepository.countByStatus("RELEASED")),
                "risks", Map.of("high", 2, "medium", 5, "low", 12),
                "trends", List.of(
                        Map.of("name", "W1", "health", 72, "velocity", 31),
                        Map.of("name", "W2", "health", 76, "velocity", 36),
                        Map.of("name", "W3", "health", 81, "velocity", 42)
                )
        );
    }
}
