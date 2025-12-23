package com.vehicle.vehicle.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vehicle.vehicle.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
