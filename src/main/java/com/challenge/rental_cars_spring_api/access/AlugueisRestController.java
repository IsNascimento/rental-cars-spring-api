package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.core.queries.ListarAlugueisQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alugueis")
@RequiredArgsConstructor
@Slf4j
public class AlugueisRestController {
    private final ListarAlugueisQuery listarAlugueisQuery;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista com os alugueis encontrados.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListarAlugueisQueryResultItem.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<Map<String, Object>> listarAlugueis() {
        try {
            log.info("Iniciando busca de aluguéis.");
            var alugueis = listarAlugueisQuery.execute();
            var valorTotalNaoPago = listarAlugueisQuery.calcularValorTotalNaoPago();
            log.info("Busca finalizada, {} aluguéis encontrados, valor total não pago: {}", alugueis.size(), valorTotalNaoPago);
            return new ResponseEntity<>(Map.of("alugueis", alugueis, "valorTotalNaoPago", valorTotalNaoPago), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erro ao listar aluguéis: {}", e.getMessage());
            return new ResponseEntity<>(Map.of("message", "Erro ao listar aluguéis"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}