package com.nexusops.agents.riskagent.controller;

import com.nexusops.agents.riskagent.dto.RiskDtos;
import com.nexusops.agents.riskagent.service.RiskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk-analysis")
@RequiredArgsConstructor
public class RiskController {
    private final RiskService service;

    @PostMapping
    RiskDtos.RiskResponse analyze(@Valid @RequestBody RiskDtos.RiskRequest request) {
        return service.analyze(request);
    }
}
