package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.service.AlugaCarroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alugarCarros")
@RequiredArgsConstructor
public class AlugaCarrosRestController {

    private final AlugaCarroService alugaCarroService;

    @GetMapping
    @Operation(summary = "Lista carros alugados e debitos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de carros alugados e em debito.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AlugaCarroService.Result.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<AlugaCarroService.Result> listarAlugueis() {
        return new ResponseEntity<>(alugaCarroService.execute(), HttpStatus.OK);
    }

}
