package com.challenge.rental_cars_spring_api.service;

import com.challenge.rental_cars_spring_api.core.dtos.ListaResultadoItemCarroDto;
import com.challenge.rental_cars_spring_api.core.interfaces.ICarro;
import com.challenge.rental_cars_spring_api.core.mapper.CarroMapper;
import com.challenge.rental_cars_spring_api.exception.SistemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrosService {

    private final ICarro icarro;

    private final CarroMapper carroMapper;

    public List<ListaResultadoItemCarroDto> execute() {
        try {
            return icarro.buscarTodos().stream().map(carroMapper::toListaResultadoItemCarro).toList();
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar carros", e.getMessage());
        }
    }
}
