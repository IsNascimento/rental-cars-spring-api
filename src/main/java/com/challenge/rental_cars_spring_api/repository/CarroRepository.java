package com.challenge.rental_cars_spring_api.repository;

import com.challenge.rental_cars_spring_api.domain.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<Carro, Long> {
}
