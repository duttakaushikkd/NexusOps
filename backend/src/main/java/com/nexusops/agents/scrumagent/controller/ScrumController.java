package com.nexusops.agents.scrumagent.controller;

import com.nexusops.agents.scrumagent.dto.ScrumDtos;
import com.nexusops.agents.scrumagent.service.ScrumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
public class ScrumController {
    private final ScrumService service;

    @GetMapping
    Page<ScrumDtos.SprintResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @PostMapping
    ScrumDtos.SprintResponse create(@Valid @RequestBody ScrumDtos.SprintRequest request) {
        return service.create(request);
    }
}
