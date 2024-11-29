package com.challenge.rental_cars_spring_api.core.queries.dtos.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Estrutura padrão para a resposta de erro")
public class CustomErrorResponse {

    @Schema(description = "Timestamp do momento em que o erro ocorreu", example = "2024-11-27T19:30:00Z")
    private LocalDateTime timestamp;

    @Schema(description = "Código de status HTTP da resposta", example = "400")
    private Integer httpStatus;

    @Schema(description = "Descrição da categoria do erro HTTP", example = "Bad Request")
    private String httpError;

    @Schema(description = "Descrição detalhada do erro", example = "Requisição inválida por causa de XYZ.")
    private String error;

    @Schema(description = "Caminho da URL que causou o erro", example = "/api/alugueis")
    private String path;
}
