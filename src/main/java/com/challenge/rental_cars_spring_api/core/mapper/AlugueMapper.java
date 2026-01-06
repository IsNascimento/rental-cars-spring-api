package com.challenge.rental_cars_spring_api.core.mapper;

import com.challenge.rental_cars_spring_api.core.domain.AlugaCarro;
import com.challenge.rental_cars_spring_api.core.dtos.ListarResultadoItemAluguelCarroDto;
import com.challenge.rental_cars_spring_api.util.Format;
import org.springframework.stereotype.Component;

@Component
public class AlugueMapper {

    public ListarResultadoItemAluguelCarroDto toListarResultadoItemAluguelCarro(AlugaCarro alugaCarro) {
        return new ListarResultadoItemAluguelCarroDto(
                alugaCarro.getDataAluguel(),
                alugaCarro.getCarro().getModelo(),
                alugaCarro.getCarro().getKm(),
                alugaCarro.getCliente().getNome(),
                Format.formatarTelefone(alugaCarro.getCliente().getTelefone()),
                alugaCarro.getDataDevolucao(),
                alugaCarro.getValor(),
                alugaCarro.isPago() ? "SIM" : "NAO"
        );
    }
}
