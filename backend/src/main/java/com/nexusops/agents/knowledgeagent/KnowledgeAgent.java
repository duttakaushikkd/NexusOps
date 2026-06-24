package com.nexusops.agents.knowledgeagent;

import com.nexusops.agents.knowledgeagent.service.KnowledgeSearchService;
import com.nexusops.orchestration.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KnowledgeAgent implements Agent {
    private final KnowledgeSearchService searchService;

    @Override
    public AgentResponse execute(AgentRequest request) {
        var result = searchService.search(request.userPrompt());
        return new AgentResponse(type(), result.answer(), Map.of("chunks", result.chunks()), 1, 1);
    }

    @Override
    public AgentType type() {
        return AgentType.KNOWLEDGE_AGENT;
    }
}
