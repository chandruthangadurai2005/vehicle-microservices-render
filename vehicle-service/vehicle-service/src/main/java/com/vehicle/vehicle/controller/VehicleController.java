package com.vehicle.vehicle.controller;

import com.vehicle.vehicle.entity.Vehicle;
import com.vehicle.vehicle.repository.VehicleRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleRepository repo;

    public VehicleController(VehicleRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Vehicle> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Vehicle getById(@PathVariable Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }
}
