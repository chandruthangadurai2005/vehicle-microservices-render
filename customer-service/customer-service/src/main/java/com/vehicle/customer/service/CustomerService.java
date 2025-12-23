package com.vehicle.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vehicle.customer.entity.Customer;
import com.vehicle.customer.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer create(Customer customer) {
        return repo.save(customer);
    }

    public List<Customer> getAll() {
        return repo.findAll();
    }
}
