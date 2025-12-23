package com.vehicle.vehicle.controller;

import com.vehicle.vehicle.entity.Vehicle;
import com.vehicle.vehicle.service.VehicleService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    public Vehicle save(@RequestBody Vehicle v) {
        return service.saveVehicle(v);
    }

    @GetMapping
    public List<Vehicle> all() {
        return service.getAllVehicles();
    }

    @GetMapping("/{id}")
    public Vehicle get(@PathVariable Long id) {
        return service.getVehicle(id);
    }
}
