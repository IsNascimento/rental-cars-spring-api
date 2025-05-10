package com.challenge.rental_cars_spring_api.core.queries.dtos;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import lombok.Getter;

@Getter
public class ListarCarrosQueryResultItem {
    private final Long id;
    private final String modelo;
    private final String placa;
    private final Double vlrDiaria;
    private final Boolean disponivel;

    public ListarCarrosQueryResultItem(Carro carro) {
        this.id = carro.getId();
        this.modelo = carro.getModelo();
        this.placa = carro.getPlaca();
        this.vlrDiaria = carro.getVlrDiaria();
        this.disponivel = carro.getDisponivel();
    }
}