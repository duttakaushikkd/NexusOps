package com.nexusops.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final SecretKey key;
    private final long accessTokenMinutes;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-token-minutes}") long accessTokenMinutes
    ) {
        this.key = Keys.hmacShaKeyFor(secret.length() >= 64 ? secret.getBytes() : Decoders.BASE64.decode("bG9jYWwtZGV2LXNlY3JldC1jaGFuZ2UtdGhpcy1iZWZvcmUtcHJvZHVjdGlvbi1sb2NhbC1kZXYtc2VjcmV0"));
        this.accessTokenMinutes = accessTokenMinutes;
    }

    public String createAccessToken(User user) {
        var roles = user.getRoles().stream().map(role -> role.getName().name()).toList();
        var now = Instant.now();
        return Jwts.builder()
                .subject(user.getEmail())
                .claims(Map.of("roles", roles, "name", user.getName(), "userId", user.getId().toString()))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessTokenMinutes * 60)))
                .signWith(key)
                .compact();
    }

    public String subject(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
