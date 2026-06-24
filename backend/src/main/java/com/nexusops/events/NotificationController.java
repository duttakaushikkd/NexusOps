package com.nexusops.events;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository repository;

    @GetMapping
    List<Notification> list() {
        return repository.findTop30ByOrderByCreatedAtDesc();
    }
}
