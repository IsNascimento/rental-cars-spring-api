package com.challenge.rental_cars_spring_api.core.dtos;

import java.math.BigDecimal;

public record ListaResultadoItemCarroDto(
        String modelo,
        String ano,
        Integer qtdPassageiros,
        Integer km,
        String fabricante,
        BigDecimal vlrDiaria
){}
