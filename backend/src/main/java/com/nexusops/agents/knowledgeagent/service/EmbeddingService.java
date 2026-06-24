package com.nexusops.agents.knowledgeagent.service;

import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {
    public String embedAsVectorLiteral(String text) {
        var values = new StringBuilder("[");
        var hash = Math.abs(text.hashCode());
        for (int i = 0; i < 1536; i++) {
            if (i > 0) {
                values.append(',');
            }
            values.append(((hash + i) % 1000) / 1000.0);
        }
        return values.append(']').toString();
    }
}
