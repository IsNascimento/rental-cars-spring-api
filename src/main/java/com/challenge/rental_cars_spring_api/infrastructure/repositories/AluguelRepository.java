package com.challenge.rental_cars_spring_api.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
}
