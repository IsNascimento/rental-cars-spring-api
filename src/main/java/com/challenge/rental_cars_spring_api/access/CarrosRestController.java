package com.challenge.rental_cars_spring_api.access;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.challenge.rental_cars_spring_api.core.queries.ListarCarrosQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/carros")
@RequiredArgsConstructor
public class CarrosRestController {

    private final ListarCarrosQuery listarCarrosQuery;

    @GetMapping
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Retorna a lista com os carros encontrados.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ListarCarrosQueryResultItem.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno no servidor ao consultar carros.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
        )
    })
    public ResponseEntity<List<ListarCarrosQueryResultItem>> listarCarros() {
        List<ListarCarrosQueryResultItem> carros = listarCarrosQuery.execute();
        return ResponseEntity.ok(carros);
    }
}
