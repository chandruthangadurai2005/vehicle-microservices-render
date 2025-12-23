package com.vehicle.vehicle.service;

import com.vehicle.vehicle.entity.Vehicle;
import com.vehicle.vehicle.repo.VehicleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository repo;

    public VehicleService(VehicleRepository repo) {
        this.repo = repo;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return repo.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return repo.findAll();
    }

    public Vehicle getVehicle(Long id) {
        return repo.findById(id).orElse(null);
    }
}
