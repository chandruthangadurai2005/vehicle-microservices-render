package com.vehicle.booking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.booking.entity.Booking;
import com.vehicle.booking.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ✅ ADMIN: update booking status
    @PutMapping("/{id}/status")
    public Booking updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return bookingService.updateStatus(id, status);
    }

    // USER: create booking
    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return bookingService.create(booking);
    }

    // USER: view own bookings
    @GetMapping("/my")
    public List<Booking> getMyBookings(
            @RequestHeader("X-CUSTOMER-ID") Long customerId
    ) {
        return bookingService.getBookingsByCustomerId(customerId);
    }

    // ADMIN: view all
    @GetMapping
    public List<Booking> getAll() {
        return bookingService.getAll();
    }
}
