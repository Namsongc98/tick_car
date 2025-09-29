package com.example.ticket_car.anotation;

import com.example.ticket_car.Enum.User.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleRequired {
    Role[] value(); // cho phép truyền 1 hoặc nhiều role
}