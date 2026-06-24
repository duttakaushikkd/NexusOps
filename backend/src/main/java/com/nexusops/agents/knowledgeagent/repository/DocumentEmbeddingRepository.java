package com.nexusops.agents.knowledgeagent.repository;

import com.nexusops.agents.knowledgeagent.model.DocumentEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentEmbeddingRepository extends JpaRepository<DocumentEmbedding, UUID> {
    List<DocumentEmbedding> findTop8ByContentContainingIgnoreCaseOrderByCreatedAtDesc(String query);
}
