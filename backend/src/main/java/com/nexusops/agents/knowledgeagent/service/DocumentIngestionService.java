package com.nexusops.agents.knowledgeagent.service;

import com.nexusops.agents.knowledgeagent.dto.KnowledgeDtos;
import com.nexusops.agents.knowledgeagent.model.Document;
import com.nexusops.agents.knowledgeagent.model.DocumentEmbedding;
import com.nexusops.agents.knowledgeagent.repository.DocumentEmbeddingRepository;
import com.nexusops.agents.knowledgeagent.repository.DocumentRepository;
import com.nexusops.events.DocumentProcessedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DocumentIngestionService {
    private final DocumentRepository documentRepository;
    private final DocumentEmbeddingRepository embeddingRepository;
    private final EmbeddingService embeddingService;
    private final ApplicationEventPublisher events;

    @Transactional
    public KnowledgeDtos.DocumentResponse ingest(KnowledgeDtos.DocumentRequest request) {
        var document = new Document();
        document.setProjectId(request.projectId());
        document.setTitle(request.title());
        document.setSourceType(request.sourceType());
        document.setContent(request.content());
        var saved = documentRepository.save(document);
        var chunks = chunk(request.content(), 1200);
        for (int i = 0; i < chunks.size(); i++) {
            var embedding = new DocumentEmbedding();
            embedding.setDocumentId(saved.getId());
            embedding.setChunkIndex(i);
            embedding.setContent(chunks.get(i));
            embedding.setEmbedding(embeddingService.embedAsVectorLiteral(chunks.get(i)));
            embeddingRepository.save(embedding);
        }
        events.publishEvent(new DocumentProcessedEvent(saved.getId(), saved.getTitle()));
        return new KnowledgeDtos.DocumentResponse(saved.getId(), saved.getTitle(), saved.getSourceType(), saved.getStatus());
    }

    private java.util.List<String> chunk(String content, int size) {
        var chunks = new ArrayList<String>();
        for (int i = 0; i < content.length(); i += size) {
            chunks.add(content.substring(i, Math.min(i + size, content.length())));
        }
        return chunks.isEmpty() ? java.util.List.of(content) : chunks;
    }
}
