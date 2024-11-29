package com.challenge.rental_cars_spring_api.tests;

import com.challenge.rental_cars_spring_api.core.domain.Carro;

import java.math.BigDecimal;

public class CarroFactory {

    public static Carro criarCarro() {
        return new Carro(
                1L,
                "Fusca",
                "1970",
                5,
                49500,
                "Volkswagen",
                BigDecimal.valueOf(100)
        );
    }

}
