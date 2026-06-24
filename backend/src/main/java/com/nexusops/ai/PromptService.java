package com.nexusops.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptService {
    private final PromptRepository repository;

    public String getPrompt(String name) {
        return repository.findByName(name)
                .map(PromptTemplate::getContent)
                .orElseGet(() -> loadDefault(name));
    }

    public List<PromptTemplate> list() {
        return repository.findAll();
    }

    @Transactional
    public PromptTemplate save(String name, String content) {
        var prompt = repository.findByName(name).orElseGet(PromptTemplate::new);
        prompt.setName(name);
        prompt.setContent(content);
        prompt.setUpdatedAt(Instant.now());
        return repository.save(prompt);
    }

    private String loadDefault(String name) {
        try {
            var resource = new ClassPathResource("prompts/" + name + ".txt");
            return resource.getContentAsString(StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Prompt not found: " + name, ex);
        }
    }
}
