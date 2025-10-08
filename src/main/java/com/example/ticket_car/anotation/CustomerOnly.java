package com.example.ticket_car.anotation;

import java.lang.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('CUSTOMER')")
public @interface CustomerOnly {
}
