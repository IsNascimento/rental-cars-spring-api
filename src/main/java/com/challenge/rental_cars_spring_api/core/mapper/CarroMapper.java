package com.challenge.rental_cars_spring_api.core.mapper;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.dtos.ListaResultadoItemCarroDto;
import org.springframework.stereotype.Component;

@Component
public class CarroMapper {

    public ListaResultadoItemCarroDto toListaResultadoItemCarro(Carro carro) {
        return new ListaResultadoItemCarroDto(
                carro.getModelo(),
                carro.getAno(),
                carro.getQtdPassageiros(),
                carro.getKm(),
                carro.getFabricante(),
                carro.getVlrDiaria()
        );
    }
}
