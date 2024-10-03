package com.challenge.rental_cars_spring_api.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.rental_cars_spring_api.core.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
