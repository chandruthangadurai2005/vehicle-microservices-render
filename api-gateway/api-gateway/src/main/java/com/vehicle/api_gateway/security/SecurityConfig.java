package com.vehicle.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchange -> exchange
                // ✅ allow auth endpoints
                .pathMatchers("/auth/**").permitAll()

                // ✅ allow everything else (JWT handled by GatewayFilter)
                .anyExchange().permitAll()
            );

        return http.build();
    }
}
