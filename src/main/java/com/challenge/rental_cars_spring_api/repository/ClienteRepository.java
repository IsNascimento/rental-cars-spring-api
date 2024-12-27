package com.challenge.rental_cars_spring_api.repository;

import com.challenge.rental_cars_spring_api.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
