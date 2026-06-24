package com.nexusops.ai;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PromptRepository extends JpaRepository<PromptTemplate, UUID> {
    Optional<PromptTemplate> findByName(String name);
}
