package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.core.queries.ListarCarrosQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carros")
@RequiredArgsConstructor
@Slf4j
public class CarrosRestController {
    private final ListarCarrosQuery listarCarrosQuery;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista com os carros encontrados.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListarCarrosQueryResultItem.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<List<ListarCarrosQueryResultItem>> listarCarros() {
        try {
            log.info("Iniciando a busca de carros.");
            var carros = listarCarrosQuery.execute();
            log.info("Busca finalizada, {} carros encontrados.", carros.size());
            return new ResponseEntity<>(carros, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erro ao listar os carros: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
