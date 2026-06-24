package com.nexusops.agents.knowledgeagent.service;

import com.nexusops.agents.knowledgeagent.dto.KnowledgeDtos;
import com.nexusops.agents.knowledgeagent.repository.DocumentEmbeddingRepository;
import com.nexusops.ai.AiService;
import com.nexusops.ai.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KnowledgeSearchService {
    private final DocumentEmbeddingRepository repository;
    private final AiService aiService;
    private final PromptService promptService;

    public KnowledgeDtos.SearchResponse search(String query) {
        var chunks = repository.findTop8ByContentContainingIgnoreCaseOrderByCreatedAtDesc(query).stream()
                .map(item -> item.getContent())
                .toList();
        var context = String.join("\n---\n", chunks);
        String answer;
        try {
            answer = aiService.complete(promptService.getPrompt("knowledge-agent-prompt"), "Question: " + query + "\nContext:\n" + context);
        } catch (Exception ex) {
            answer = chunks.isEmpty() ? "No matching knowledge was found." : "Relevant knowledge found:\n" + context;
        }
        return new KnowledgeDtos.SearchResponse(answer, chunks);
    }
}
