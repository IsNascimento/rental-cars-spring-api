package com.challenge.rental_cars_spring_api.infrastructure.openapi;

import com.challenge.rental_cars_spring_api.core.queries.dtos.AluguelResponseDTO;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ResultadoProcessamentoArquivoDTO;
import com.challenge.rental_cars_spring_api.core.queries.dtos.errors.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Aluguel")
public interface AluguelRestControllerOpenApi {

    @GetMapping
    @Operation(description = "Lista todos os alugueis cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de alugueis encontrados.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AluguelResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Formato inválido do arquivo.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))})})
    ResponseEntity<AluguelResponseDTO> listarAlugueis();

    @PostMapping(value = "/carregar-arquivo", consumes = "multipart/form-data")
    @Operation(description = "Processa o arquivo de aluguel e retorna o resultado do processamento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo processado com sucesso.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResultadoProcessamentoArquivoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Formato inválido do arquivo.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))})})
    ResponseEntity<ResultadoProcessamentoArquivoDTO> carregarArquivo(@RequestParam("file") MultipartFile file);

}
