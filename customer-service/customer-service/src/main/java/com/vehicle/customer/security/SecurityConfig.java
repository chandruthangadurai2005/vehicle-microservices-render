package com.vehicle.customer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF for service-to-service POST
            .csrf(csrf -> csrf.disable())

            // ✅ DISABLE all default auth mechanisms
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable())

            .authorizeHttpRequests(auth -> auth
                // ✅ allow register-time customer creation
                .requestMatchers("/customer", "/customer/**").permitAll()

                // block everything else
                .anyRequest().denyAll()
            );

        return http.build();
    }
}
