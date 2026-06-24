package com.nexusops.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiService {
    private final ChatClient.Builder chatClientBuilder;
    private final SystemSettingRepository settings;

    @Value("${app.ai.supported-models}")
    private String supportedModels;

    public String complete(String systemPrompt, String userPrompt) {
        return complete(systemPrompt, userPrompt, currentModel());
    }

    public String complete(String systemPrompt, String userPrompt, String model) {
        if (!supportedModels().contains(model)) {
            throw new IllegalArgumentException("Unsupported model: " + model);
        }
        return chatClientBuilder.build()
                .prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .options(OllamaOptions.builder().model(model).build())
                .call()
                .content();
    }

    public List<String> supportedModels() {
        return Arrays.stream(supportedModels.split(",")).map(String::trim).toList();
    }

    public String currentModel() {
        return settings.findById("ollama.model").map(SystemSetting::getValue).orElse("llama3");
    }

    public void switchModel(String model) {
        if (!supportedModels().contains(model)) {
            throw new IllegalArgumentException("Unsupported model: " + model);
        }
        var setting = settings.findById("ollama.model").orElseGet(SystemSetting::new);
        setting.setKey("ollama.model");
        setting.setValue(model);
        setting.setUpdatedAt(Instant.now());
        settings.save(setting);
    }
}
