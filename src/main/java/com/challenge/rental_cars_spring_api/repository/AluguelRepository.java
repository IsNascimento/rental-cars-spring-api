package com.challenge.rental_cars_spring_api.repository;

import com.challenge.rental_cars_spring_api.domain.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    List<Aluguel> findByDataAluguelAndCarroModelo(LocalDate dataAluguel, String modelo);

    List<Aluguel> findByDataAluguel(LocalDate dataAluguel);

    List<Aluguel> findByCarroModelo(String modelo);

}
