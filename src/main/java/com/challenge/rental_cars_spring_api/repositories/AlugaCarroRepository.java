package com.challenge.rental_cars_spring_api.repositories;

import com.challenge.rental_cars_spring_api.core.domain.AlugaCarro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface AlugaCarroRepository extends JpaRepository<AlugaCarro, Long> {

    @Query("SELECT SUM(al.valor) FROM AlugaCarro al WHERE al.pago = false")
    BigDecimal somarDebito();
}
