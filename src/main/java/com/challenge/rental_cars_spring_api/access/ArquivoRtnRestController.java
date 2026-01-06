package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.service.ArquivoRtnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/arquivoRtn")
@RequiredArgsConstructor
public class ArquivoRtnRestController {

    private final ArquivoRtnService arquivoRtnService;

    @PostMapping("/extrairArquivoRtn")
    @Operation(summary = "Extrair informações arquivo .rtn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Processamento de dados de arquivo .rtn"),
            @ApiResponse(responseCode = "400", description = "Erro no formato do arquivo"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")})
    public ResponseEntity<Map<String, Object>> processarAluguel() {
        Map<String, Object> resultado = arquivoRtnService.processarArquivo();
        return ResponseEntity.ok(resultado);
    }
}
