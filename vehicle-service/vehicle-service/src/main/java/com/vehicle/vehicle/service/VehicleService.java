package com.vehicle.vehicle.service;

import com.vehicle.vehicle.entity.Vehicle;
import com.vehicle.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository repo;

    public VehicleService(VehicleRepository repo) {
        this.repo = repo;
    }

    public Vehicle create(Vehicle vehicle) {
        return repo.save(vehicle);
    }

    public List<Vehicle> getAll() {
        return repo.findAll();
    }
}
