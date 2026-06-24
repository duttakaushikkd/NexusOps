package com.nexusops.agents.releaseagent.controller;

import com.nexusops.agents.releaseagent.dto.ReleaseDtos;
import com.nexusops.agents.releaseagent.service.ReleaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/releases")
@RequiredArgsConstructor
public class ReleaseController {
    private final ReleaseService service;

    @GetMapping
    Page<ReleaseDtos.ReleaseResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @PostMapping
    ReleaseDtos.ReleaseResponse create(@Valid @RequestBody ReleaseDtos.ReleaseRequest request) {
        return service.create(request);
    }
}
