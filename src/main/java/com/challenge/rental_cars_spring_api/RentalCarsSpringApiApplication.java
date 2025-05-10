package com.challenge.rental_cars_spring_api;

import java.util.TimeZone;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(
    exclude = SecurityAutoConfiguration.class,
    scanBasePackages = {
        "com.challenge.config",
        "com.challenge.rental_cars_spring_api"
    }
)
public class RentalCarsSpringApiApplication {

    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    public static void main(String[] args) {
        SpringApplication.run(RentalCarsSpringApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(UTC);
    }
}
