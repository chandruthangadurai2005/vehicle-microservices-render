package com.vehicle.booking.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vehicle.booking.entity.Booking;
import com.vehicle.booking.repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository repo;
    private final RestTemplate restTemplate;

    public BookingService(BookingRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    // ================= USER CREATE BOOKING =================
    public Booking create(Booking booking) {

        booking.setBookingTime(LocalDateTime.now());

        if (booking.getStatus() == null) {
            booking.setStatus("CONFIRMED");
        }

        Booking saved = repo.save(booking);

        // 🔔 Notify user on creation
        sendNotification(
                saved.getCustomerId(),
                "Your booking has been CONFIRMED"
        );

        return saved;
    }

    // ================= ADMIN UPDATE STATUS =================
   // ================= ADMIN UPDATE STATUS =================
public Booking updateStatus(Long id, String status) {

    Booking booking = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

    booking.setStatus(status);
    Booking saved = repo.save(booking);

    System.out.println("🔁 STATUS UPDATED TO: " + status);

    sendNotification(
            saved.getCustomerId(),
            "Your booking status is " + status
    );

    if ("COMPLETED".equalsIgnoreCase(status)) {

        System.out.println("🚀 ENTERED BILLING BLOCK");

        Long vehicleId = saved.getVehicleId();
        if (vehicleId == null) {
            throw new RuntimeException("Vehicle ID missing for booking " + saved.getId());
        }

        // 🔹 Get vehicle details
        Map<String, Object> vehicle =
                restTemplate.getForObject(
                        "http://VEHICLE-SERVICE/vehicle/" + vehicleId,
                        Map.class
                );

        System.out.println("🚗 VEHICLE RESPONSE = " + vehicle);

        if (vehicle == null || vehicle.get("rent") == null) {
            throw new RuntimeException("Vehicle rent missing for vehicle " + vehicleId);
        }

        Double amount = Double.valueOf(vehicle.get("rent").toString());

        // 🔹 Create bill payload
        Map<String, Object> bill = new HashMap<>();
        bill.put("bookingId", saved.getId());
        bill.put("customerId", saved.getCustomerId());
        bill.put("amount", amount);
        bill.put("status", "UNPAID");

        // 🔹 CALL BILLING SERVICE (🔥 THIS WAS MISSING 🔥)
        restTemplate.postForObject(
                "http://BILLING-SERVICE/billing",
                bill,
                Object.class
        );

        System.out.println("✅ BILL CREATED SUCCESSFULLY");
    }

    return saved;
}


    // ================= NOTIFICATION =================
    private void sendNotification(Long customerId, String message) {
        try {
            Map<String, Object> notification = new HashMap<>();
            notification.put("customerId", customerId);
            notification.put("message", message);
            notification.put("type", "SYSTEM");
            notification.put("status", "SENT");

            restTemplate.postForObject(
                    "http://NOTIFICATION-SERVICE/notification",
                    notification,
                    Object.class
            );
        } catch (Exception e) {
            System.err.println("❌ Notification service failed");
            e.printStackTrace();
        }
    }

    // ================= READ =================
    public List<Booking> getAll() {
        return repo.findAll();
    }

    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return repo.findByCustomerId(customerId);
    }
}
