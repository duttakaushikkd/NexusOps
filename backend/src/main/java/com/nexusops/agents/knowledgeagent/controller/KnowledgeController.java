package com.nexusops.agents.knowledgeagent.controller;

import com.nexusops.agents.knowledgeagent.dto.KnowledgeDtos;
import com.nexusops.agents.knowledgeagent.service.DocumentIngestionService;
import com.nexusops.agents.knowledgeagent.service.KnowledgeSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class KnowledgeController {
    private final DocumentIngestionService ingestionService;
    private final KnowledgeSearchService searchService;

    @PostMapping("/upload")
    KnowledgeDtos.DocumentResponse upload(@Valid @RequestBody KnowledgeDtos.DocumentRequest request) {
        return ingestionService.ingest(request);
    }

    @PostMapping("/search")
    KnowledgeDtos.SearchResponse search(@Valid @RequestBody KnowledgeDtos.SearchRequest request) {
        return searchService.search(request.query());
    }
}
