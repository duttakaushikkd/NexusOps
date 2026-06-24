package com.nexusops.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainEventListener {
    private final NotificationRepository repository;

    @EventListener
    void onProjectCreated(ProjectCreatedEvent event) {
        save("PROJECT_CREATED", "Project created", "Project " + event.name() + " is ready for planning.");
    }

    @EventListener
    void onSprintCreated(SprintCreatedEvent event) {
        save("SPRINT_CREATED", "Sprint created", "Sprint " + event.name() + " has been created.");
    }

    @EventListener
    void onReleaseCreated(ReleaseCreatedEvent event) {
        save("RELEASE_CREATED", "Release created", "Release " + event.name() + " has been created.");
    }

    @EventListener
    void onDocumentProcessed(DocumentProcessedEvent event) {
        save("DOCUMENT_PROCESSED", "Document processed", event.title() + " has been indexed.");
    }

    @EventListener
    void onRiskDetected(RiskDetectedEvent event) {
        save("RISK_DETECTED", "Risk detected", "Risk score " + event.riskScore() + " for " + event.scope());
    }

    private void save(String type, String title, String message) {
        var notification = new Notification();
        notification.setType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        repository.save(notification);
    }
}
