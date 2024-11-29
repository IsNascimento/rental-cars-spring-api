package com.challenge.rental_cars_spring_api.core.queries.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para resultado do processamento de um arquivo de registros")
public class ResultadoProcessamentoArquivoDTO {

    @Schema(description = "Nome do arquivo processado", example = "registros.rtn")
    private String nomeArquivo;

    @Schema(description = "Total de registros presentes no arquivo", example = "100")
    private Integer totalRegistros;

    @Schema(description = "Total de registros que foram processados com sucesso", example = "95")
    private Integer totalRegistrosProcessados;

    @Schema(description = "Total de registros que apresentaram erros durante o processamento", example = "5")
    private Integer totalRegistrosComErro;


    public ResultadoProcessamentoArquivoDTO(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.totalRegistros = 0;
        this.totalRegistrosProcessados = 0;
        this.totalRegistrosComErro = 0;
    }
}
