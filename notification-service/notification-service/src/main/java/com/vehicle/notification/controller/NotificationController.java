package com.vehicle.notification.controller;

import com.vehicle.notification.entity.Notification;
import com.vehicle.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        notification.setStatus("SENT");
        return service.create(notification);
    }

    // USER
    @GetMapping("/user")
    public List<Notification> getByCustomer(
            @RequestHeader("X-CUSTOMER-ID") Long customerId
    ) {
        return service.getByCustomerId(customerId);
    }

    // ADMIN
    @GetMapping
    public List<Notification> getAll() {
        return service.getAll();
    }
}
