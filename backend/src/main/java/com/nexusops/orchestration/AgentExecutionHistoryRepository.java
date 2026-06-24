package com.nexusops.orchestration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AgentExecutionHistoryRepository extends JpaRepository<AgentExecutionHistory, UUID> {
    List<AgentExecutionHistory> findTop50ByOrderByCreatedAtDesc();
}
