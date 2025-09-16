package com.example.ticket_car.config;

import com.example.ticket_car.entity.User;
import com.example.ticket_car.filter.JwtAuthFilter;
import com.example.ticket_car.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.ticket_car.exception.CustomAccessDeniedHandler;
import com.example.ticket_car.exception.CustomAuthEntryPoint;
import com.example.ticket_car.Enum.User.Role;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
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
                        .requestMatchers("/api/**").permitAll() // login, register không cần token
                        .anyRequest().authenticated()                // các API khác cần JWT
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPoint)   // lỗi 401
                        .accessDeniedHandler(accessDeniedHandler)   // lỗi 403
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return email -> {
//            User user = userRepository.findByEmail(email)
//                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
//            return org.springframework.security.core.userdetails.User
//                    .withUsername(user.getEmail())
//                    .password(user.getPassword())
//                    .roles(user.getRole().name())
//                    .build();
//        };
//    }
}
