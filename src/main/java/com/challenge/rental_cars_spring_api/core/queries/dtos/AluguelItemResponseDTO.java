package com.challenge.rental_cars_spring_api.core.queries.dtos;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.utils.TelefoneUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "DTO para resposta de item de aluguel")
public record AluguelItemResponseDTO (
        @Schema(description = "Data do aluguel", example = "2024-11-27")
        Date dataAluguel,

        @Schema(description = "Modelo do carro alugado", example = "Toyota Corolla")
        String modeloCarro,

        @Schema(description = "Quilometragem atual do carro", example = "12000")
        Integer kmCarro,

        @Schema(description = "Nome do cliente que realizou o aluguel", example = "John Doe")
        String nomeCliente,

        @Schema(description = "Telefone do cliente formatado", example = "+55(11)91234-5678")
        String telefoneCliente,

        @Schema(description = "Data de devolução do carro", example = "2024-12-01")
        Date dataDevolucao,

        @Schema(description = "Valor total do aluguel", example = "350.00")
        BigDecimal valor,

        @Schema(description = "Indicador se o aluguel foi pago", example = "SIM", allowableValues = {"SIM", "NAO"})
        String pago
) {
    public static AluguelItemResponseDTO from(Aluguel aluguel) {
        return new AluguelItemResponseDTO(
                aluguel.getDataAluguel(),
                aluguel.getCarro().getModelo(),
                aluguel.getCarro().getKm(),
                aluguel.getCliente().getNome(),
                TelefoneUtils.formatarTelefone("55" + aluguel.getCliente().getTelefone()),
                aluguel.getDataDevolucao(),
                aluguel.getValor(),
                aluguel.getPago() ? "SIM" : "NAO"
        );
    }
}
