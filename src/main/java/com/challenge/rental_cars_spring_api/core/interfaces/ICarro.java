package com.challenge.rental_cars_spring_api.core.interfaces;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import java.util.List;
import java.util.Optional;

public interface ICarro {

    List<Carro> buscarTodos();
    Optional<Carro> buscarPorId(Long id);
    boolean isExistId(Long id);
}
