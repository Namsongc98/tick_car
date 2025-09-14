package com.example.ticket_car.Dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.ticket_car.Enum.User.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String name;   // optional
    private String phone;  // optional
    private Role role;     // optional

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;  // required

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password; // required
}
