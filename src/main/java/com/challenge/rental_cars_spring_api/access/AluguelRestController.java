package com.challenge.rental_cars_spring_api.access;

import java.util.InputMismatchException;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.rental_cars_spring_api.core.queries.dtos.*;
import com.challenge.rental_cars_spring_api.core.services.AluguelService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/alugueis")
public class AluguelRestController {

    private final AluguelService aluguelService;

    public AluguelRestController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @PostMapping(
        value = "/importar-arquivo",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Arquivo importado com sucesso.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ResultadoImportacaoAluguelDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno no servidor ao importar arquivo.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
        )
    })
    public ResponseEntity<ResultadoImportacaoAluguelDTO> importarArquivo(
        @RequestParam("file") MultipartFile file
    ) {
        String filename = file.getOriginalFilename();

        if (filename == null || !filename.endsWith(".rtn")) {
            throw new InputMismatchException("Tipo de arquivo inválido. O arquivo deve ser do tipo .rtn.");
        }

        ResultadoImportacaoAluguelDTO resultado = aluguelService.processarArquivo(file);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Retorna a lista com os alugueis encontrados.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ResultadoListagemAluguelDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno no servidor ao consultar lista de alugueis.",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
        )
    })
    public ResponseEntity<ResultadoListagemAluguelDTO> listarAlugueis() {
        ResultadoListagemAluguelDTO resultado = aluguelService.consultarAluguel();
        return ResponseEntity.ok(resultado);
    }
}
