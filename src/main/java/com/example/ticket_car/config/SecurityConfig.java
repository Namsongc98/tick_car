package com.example.ticket_car.config;

import com.example.ticket_car.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.ticket_car.exception.CustomAccessDeniedHandler;
import com.example.ticket_car.exception.CustomAuthEntryPoint;
import com.example.ticket_car.Enum.User.Role;

public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomAuthEntryPoint authEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter,
                          CustomAuthEntryPoint authEntryPoint,
                          CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // tắt CSRF cho REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/api/ticket/book").hasRole(Role.CUSTOMER.name())
                        .requestMatchers("/api/trip/**").hasAnyRole(Role.STAFF.name(), Role.ADMIN.name())
                        .requestMatchers("/api/**").hasRole(Role.ADMIN.name())// login, register không cần token
                        .anyRequest().authenticated()                // các API khác cần JWT
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPoint)   // lỗi 401
                        .accessDeniedHandler(accessDeniedHandler)   // lỗi 403
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
