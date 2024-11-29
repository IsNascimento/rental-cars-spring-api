package com.challenge.rental_cars_spring_api.core.queries.dtos;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para resultado da consulta de listagem de carros")
public record ListarCarrosQueryResultItem(
        @Schema(description = "Identificador único do carro", example = "1")
        Long id,

        @Schema(description = "Modelo do carro", example = "Toyota Corolla")
        String modelo
) {

    public static ListarCarrosQueryResultItem from(Carro carro) {
        return new ListarCarrosQueryResultItem(carro.getId(), carro.getModelo());
    }
}
