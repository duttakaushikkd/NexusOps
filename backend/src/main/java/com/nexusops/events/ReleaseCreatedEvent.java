package com.nexusops.events;

import java.util.UUID;

public record ReleaseCreatedEvent(UUID releaseId, String name) {
}
