package com.vehicle.billing_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vehicle.billing_service.entity.Billing;
import com.vehicle.billing_service.repository.BillingRepository;

@Service
public class BillingService {

    private final BillingRepository repo;

    public BillingService(BillingRepository repo) {
        this.repo = repo;
    }

    public Billing save(Billing bill) {
        if (bill.getStatus() == null) {
            bill.setStatus("UNPAID");
        }
        return repo.save(bill); // 🔥 THIS LINE IS CRITICAL
    }

    public List<Billing> getAll() {
        return repo.findAll();
    }

    public List<Billing> getByCustomerId(Long customerId) {
        return repo.findByCustomerId(customerId);
    }
   
public Billing markPaid(Long id) {
    Billing bill = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Bill not found"));

    bill.setStatus("PAID");
    return repo.save(bill);
}

    public Double getTotalRevenue() {
        return repo.sumTotalRevenue();
    }
}
