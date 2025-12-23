package com.vehicle.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vehicle.auth.dto.Customer;
import com.vehicle.auth.entity.User;
import com.vehicle.auth.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RestTemplate restTemplate
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
    }

    // ================= REGISTER =================
    public User register(String username, String password, boolean isAdmin) {

        Optional<User> existing =
                userRepository.findByUsername(username);

        if (existing.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        // 🔥 ROLE decided ONLY here
        user.setRole(isAdmin ? "ADMIN" : "USER");

        // 🔥 Create Customer
        Customer customer = new Customer();
        customer.setName(username);
        customer.setEmail(username + "@mail.com");
        customer.setPhone("0000000000");
        customer.setAddress(isAdmin ? "ADMIN" : "NA");

        Customer savedCustomer =
                restTemplate.postForObject(
                        "http://CUSTOMER-SERVICE/customer",
                        customer,
                        Customer.class
                );

        if (savedCustomer == null || savedCustomer.getId() == null) {
            throw new RuntimeException("Customer creation failed");
        }

        user.setCustomerId(savedCustomer.getId());

        return userRepository.save(user);
    }
}
