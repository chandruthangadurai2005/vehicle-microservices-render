package com.vehicle.notification.service;

import com.vehicle.notification.entity.Notification;
import com.vehicle.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public Notification create(Notification n) {
        return repo.save(n);
    }

    public List<Notification> getByCustomerId(Long customerId) {
        return repo.findByCustomerId(customerId);
    }

    public List<Notification> getAll() {
        return repo.findAll();
    }
}
