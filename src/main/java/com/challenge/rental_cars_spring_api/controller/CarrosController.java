package com.challenge.rental_cars_spring_api.controller;

import com.challenge.rental_cars_spring_api.domain.dto.ListarCarrosResponse;
import com.challenge.rental_cars_spring_api.service.CarroService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carros")
@RequiredArgsConstructor
public class CarrosController {

    private final CarroService carroService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carros encontrada com sucesso.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListarCarrosResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Nenhum carro encontrado.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<List<ListarCarrosResponse>> listarCarros() {
        return ResponseEntity.ok(carroService.listarCarros());
    }
}
