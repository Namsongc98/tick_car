package com.example.ticket_car.anotation;
import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('STAFF')")
public @interface StaffOnly {
}
