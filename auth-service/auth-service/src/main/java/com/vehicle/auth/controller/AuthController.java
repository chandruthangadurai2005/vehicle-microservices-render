package com.vehicle.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.vehicle.auth.dto.LoginRequest;
import com.vehicle.auth.entity.User;
import com.vehicle.auth.repository.UserRepository;
import com.vehicle.auth.security.JwtUtil;
import com.vehicle.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 🔐 ADMIN SECRET (can move to application.yml)
    private static final String ADMIN_SECRET = "ADMIN_SECRET_123";

    public AuthController(
            AuthService authService,
            JwtUtil jwtUtil,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ================= REGISTER (USER / ADMIN) =================
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody LoginRequest request,
            @RequestHeader(value = "X-ADMIN-KEY", required = false) String adminKey
    ) {

        boolean isAdmin = ADMIN_SECRET.equals(adminKey);

        User user = authService.register(
                request.getUsername(),
                request.getPassword(),
                isAdmin
        );

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", isAdmin ? "Admin registered" : "User registered");
        resp.put("role", user.getRole());

        return ResponseEntity.ok(resp);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody LoginRequest req
    ) {

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole());
response.put("customerId", user.getCustomerId().toString());

        return ResponseEntity.ok(response);
    }
}
