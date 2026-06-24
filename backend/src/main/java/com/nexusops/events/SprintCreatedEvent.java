package com.nexusops.events;

import java.util.UUID;

public record SprintCreatedEvent(UUID sprintId, String name) {
}
