package com.nexusops.events;

import java.util.UUID;

public record ProjectCreatedEvent(UUID projectId, String name) {
}
