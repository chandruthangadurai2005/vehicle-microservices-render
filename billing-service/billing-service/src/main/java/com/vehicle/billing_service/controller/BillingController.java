package com.vehicle.billing_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.billing_service.entity.Billing;
import com.vehicle.billing_service.service.BillingService;
@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService service;

    public BillingController(BillingService service) {
        this.service = service;
    }
   /*  @PostMapping
public Billing create(@RequestBody Billing bill) {
    return service.save(bill);
}*/

    // USER: view my bills
    @GetMapping("/my")
    public List<Billing> getMyBills(
            @RequestHeader("X-CUSTOMER-ID") Long customerId
    ) {
        return service.getByCustomerId(customerId);
    }
   @GetMapping("/total")
public Double getTotalRevenue() {
    return service.getTotalRevenue();
}
@PutMapping("/{id}/pay")
public Billing payBill(@PathVariable Long id) {
    return service.markPaid(id);
}
 @PostMapping
public ResponseEntity<?> createBill(@RequestBody Billing bill) {
    System.out.println("🔥 BILLING CONTROLLER HIT 🔥");
    System.out.println("Bill data: " + bill);
    return ResponseEntity.ok(service.save(bill));
}

    // ADMIN: view all
    @GetMapping
    public List<Billing> getAll() {
        return service.getAll();
    }
}
