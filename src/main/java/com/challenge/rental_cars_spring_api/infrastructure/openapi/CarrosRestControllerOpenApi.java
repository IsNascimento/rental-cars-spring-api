package com.challenge.rental_cars_spring_api.infrastructure.openapi;

import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.challenge.rental_cars_spring_api.core.queries.dtos.errors.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Carros")
public interface CarrosRestControllerOpenApi {

    @GetMapping
    @Operation(description = "Lista todos os carros cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista com os carros encontrados.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ListarCarrosQueryResultItem.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))})})
    ResponseEntity<List<ListarCarrosQueryResultItem>> listarCarros();
}
