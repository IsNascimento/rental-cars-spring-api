package com.challenge.rental_cars_spring_api.core.queries.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "DTO para resposta de uma lista de alugueis")
public record AluguelResponseDTO(
        @Schema(description = "Valor total das reservas que não foram pagas", example = "1250.50")
        BigDecimal totalNaoPago,

        @Schema(description = "Lista de alugueis")
        List<AluguelItemResponseDTO> alugueis
) {
    public static AluguelResponseDTO from(List<AluguelItemResponseDTO> alugueis) {
        BigDecimal totalNaoPago = alugueis.stream()
                .filter(aluguel -> aluguel.pago().equals("NAO"))
                .map(AluguelItemResponseDTO::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new AluguelResponseDTO(totalNaoPago, alugueis);
    }
}
