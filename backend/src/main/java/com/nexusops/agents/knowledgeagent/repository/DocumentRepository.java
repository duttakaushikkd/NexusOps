package com.nexusops.agents.knowledgeagent.repository;

import com.nexusops.agents.knowledgeagent.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
