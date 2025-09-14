package com.challenge.rental_cars_spring_api.access;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.queries.AluguelQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/aluguel")
@RequiredArgsConstructor
public class AluguelRestController {
    private final AluguelQuery aluguelService;
  

    @GetMapping("/listarAluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista com os alugueis encontrados.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListarCarrosQueryResultItem.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<List<Aluguel>> listarCarros() {
        return new ResponseEntity<>(aluguelService.listarAluguel(), HttpStatus.OK);
    }
    
    @PostMapping("/processar-arquivo")
    public ResponseEntity<Map<String, String>> processarArquivo() {
        aluguelService.processarArquivoRentReport();
        return ResponseEntity.ok(Collections.singletonMap("mensagem", "Processamento concluído."));
    }

}
