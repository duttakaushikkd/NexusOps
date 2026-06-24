package com.nexusops.events;

import java.util.UUID;

public record DocumentProcessedEvent(UUID documentId, String title) {
}
