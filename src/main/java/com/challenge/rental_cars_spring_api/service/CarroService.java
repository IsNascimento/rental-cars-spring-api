package com.challenge.rental_cars_spring_api.service;

import com.challenge.rental_cars_spring_api.domain.Carro;
import com.challenge.rental_cars_spring_api.domain.dto.ListarCarrosResponse;

import java.util.List;

public interface CarroService {
    List<ListarCarrosResponse> listarCarros();
    Carro obterCarroPorId(Long id);
}
