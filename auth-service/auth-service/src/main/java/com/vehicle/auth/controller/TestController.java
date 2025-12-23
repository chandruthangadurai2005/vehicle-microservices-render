package com.vehicle.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/user/profile")
    public String userProfile() {
        return "USER profile accessed";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "ADMIN dashboard accessed";
    }
}
