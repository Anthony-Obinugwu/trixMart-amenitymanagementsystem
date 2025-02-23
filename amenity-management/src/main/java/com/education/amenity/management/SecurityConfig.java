package com.education.amenity.management;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF is disabled
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/whatsapp/incoming").permitAll() // Allow Twilio requests
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.disable()) // Disable basic auth if not needed
                .formLogin(form -> form.disable()); // Disable form login if not needed

        return http.build();
    }
}

