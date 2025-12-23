package com.vehicle.billing_service.repository;

import com.vehicle.billing_service.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface BillingRepository extends JpaRepository<Billing, Long> {

    List<Billing> findByCustomerId(Long customerId);

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Billing b")
    Double sumTotalRevenue();
}

