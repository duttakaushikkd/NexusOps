package com.nexusops.security;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {
    @Test
    void extractsSubjectFromToken() {
        var service = new JwtService("local-dev-secret-change-this-before-production-local-dev-secret", 30);
        var user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("architect@nexusops.local");
        user.setName("Architect");

        var token = service.createAccessToken(user);

        assertThat(service.subject(token)).isEqualTo("architect@nexusops.local");
    }
}
