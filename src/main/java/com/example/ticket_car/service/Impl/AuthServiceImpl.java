package com.example.ticket_car.service.Impl;

import com.example.ticket_car.Dto.request.UserRequestDto;
import com.example.ticket_car.Enum.User.Role;
import com.example.ticket_car.repository.UserRepository;
import com.example.ticket_car.service.AuthService;
import com.example.ticket_car.entity.User;

import com.example.ticket_car.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
@Getter
@Setter
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(@Valid UserRequestDto user) {

        String name = user.getName();
        String email = user.getEmail();

        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already exists");
        }
        if(userRepository.existsByName(name)){
            throw new IllegalArgumentException("Name already exists");
        }

        String phone = user.getPhone();
        String password = passwordEncoder.encode(user.getPassword());
        Role role = (user.getRole() == null) ? Role.CUSTOMER : user.getRole();

        try{
            User newUser = User.builder()
                    .name(name)
                    .email(email)
                    .phone(phone)
                    .password(password)
                    .role(role)
                    .createdAt(Instant.now())
                    .build();
            return userRepository.save(newUser);
        }catch (Exception e){
            throw new RuntimeException(e);
        }


    }

    public User login(@Valid UserRequestDto user){
        String email = user.getEmail();
        String password = user.getPassword();

        User checkUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(password, checkUser.getPassword())){
            return checkUser;
        }else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
