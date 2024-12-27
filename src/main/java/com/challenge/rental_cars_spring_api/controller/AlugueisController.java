package com.challenge.rental_cars_spring_api.controller;

import com.challenge.rental_cars_spring_api.domain.dto.AluguelDTO;
import com.challenge.rental_cars_spring_api.domain.dto.ListarCarrosResponse;
import com.challenge.rental_cars_spring_api.exception.BusinessException;
import com.challenge.rental_cars_spring_api.service.AluguelService;
import com.challenge.rental_cars_spring_api.util.MessageUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/aluguel")
@RequiredArgsConstructor
public class AlugueisController {

    private final AluguelService aluguelService;
    private final MessageUtil messageUtil;

    @PostMapping(value = "/processar-arquivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carros encontrada com sucesso.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListarCarrosResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Nenhum carro encontrado.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<Void> processarArquivo(@RequestParam("arquivo") MultipartFile arquivo) {
        aluguelService.processarArquivoAlugueis(arquivo);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carros encontrada com sucesso.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListarCarrosResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Nenhum carro encontrado.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<List<AluguelDTO>> listarAlugueis() {
        return ResponseEntity.ok(aluguelService.listarAlugueis());
    }

    @GetMapping("/filtrar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carros encontrada com sucesso.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ListarCarrosResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Nenhum carro encontrado.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<List<AluguelDTO>> filtrarAlugueis(
            @RequestParam(value = "data", required = false) String data,
            @RequestParam(value = "modelo", required = false) String modelo) {
        try {
            List<AluguelDTO> alugueis = aluguelService.filtrarAlugueis(data, modelo);
            return ResponseEntity.ok(alugueis);
        } catch (DateTimeParseException ex) {
            String mensagemErro = messageUtil.getMessage("erro.data.invalida", "data");
            throw new BusinessException(mensagemErro);
        }
    }



}

