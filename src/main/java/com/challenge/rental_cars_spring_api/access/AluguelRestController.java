package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.core.queries.ListarAlugueisQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.RetornoConsultaAlugueis;
import com.challenge.rental_cars_spring_api.core.service.UploadAluguelService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/aluguel")
@RequiredArgsConstructor
public class AluguelRestController {

    private final UploadAluguelService uploadAluguelService;
    private final ListarAlugueisQuery listarAlugueisQuery;

    @PostMapping("/upload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo processado com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<Void> uploadAlugueis(@RequestParam("file") MultipartFile file) throws IOException {
        uploadAluguelService.execute(file);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de alugueis encontrados.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RetornoConsultaAlugueis.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<RetornoConsultaAlugueis> listarAlugueis() {
        return new ResponseEntity<>(listarAlugueisQuery.execute(), HttpStatus.OK);
    }
}
