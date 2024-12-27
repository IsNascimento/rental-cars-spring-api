package com.challenge.rental_cars_spring_api.domain.dto;

import com.challenge.rental_cars_spring_api.domain.Carro;

public record ListarCarrosResponse(Long id, String modelo) {

    public static ListarCarrosResponse from(Carro carro) {
        return new ListarCarrosResponse(carro.getId(), carro.getModelo());
    }
}
