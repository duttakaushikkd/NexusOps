package com.nexusops.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI nexusOpsOpenApi() {
        return new OpenAPI().info(new Info()
                .title("NexusOps AI Project Manager API")
                .version("0.1.0")
                .description("Enterprise AI project management platform API"));
    }
}
