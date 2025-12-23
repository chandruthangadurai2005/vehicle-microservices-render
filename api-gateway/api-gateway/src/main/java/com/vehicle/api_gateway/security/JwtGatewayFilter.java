package com.vehicle.api_gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class JwtGatewayFilter
        extends AbstractGatewayFilterFactory<JwtGatewayFilter.Config> {

    private final JwtUtil jwtUtil;

    public JwtGatewayFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            String path = exchange.getRequest().getURI().getPath();
            String method = exchange.getRequest().getMethod().name();

            /* =================================================
               1️⃣ INTERNAL SERVICE CALLS (NO JWT)
               ================================================= */
            if (
                (path.startsWith("/billing") && method.equals("POST")) ||
                (path.startsWith("/notification") && method.equals("POST"))
            ) {
                return chain.filter(exchange);
            }

            /* =================================================
               2️⃣ PUBLIC ENDPOINTS (NO JWT)
               ================================================= */
            if (
                path.startsWith("/auth") ||
                (path.equals("/customer") && method.equals("POST"))
            ) {
                return chain.filter(exchange);
            }

            /* =================================================
               3️⃣ AUTH HEADER CHECK
               ================================================= */
            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            if (!jwtUtil.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String role = jwtUtil.extractRole(token);

            /* =================================================
               4️⃣ USER ALLOWED ENDPOINTS
               ================================================= */
            if (
                (path.equals("/vehicle") && method.equals("GET")) ||
                (path.equals("/booking") && method.equals("POST")) ||
                (path.equals("/booking/my") && method.equals("GET")) ||
                (path.equals("/billing/my") && method.equals("GET")) ||
                path.matches("/billing/\\d+/pay") ||
                (path.equals("/notification/user") && method.equals("GET"))
            ) {
                return chain.filter(exchange);
            }

            /* =================================================
               5️⃣ ADMIN ONLY ENDPOINTS
               ================================================= */
            if (
                (path.startsWith("/booking") && method.equals("PUT")) ||
                path.startsWith("/vehicle") ||
                path.startsWith("/billing") ||
                path.startsWith("/notification") ||
                path.startsWith("/customer")
            ) {
                if (!"ADMIN".equals(role)) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
                return chain.filter(exchange);
            }

            /* =================================================
               6️⃣ BLOCK EVERYTHING ELSE
               ================================================= */
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        };
    }

    public static class Config {
    }
}
