package com.challenge.rental_cars_spring_api.repositories;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.interfaces.ICarro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarroRepositoryImp implements ICarro {

    private final CarroRepository carroRepository;

    @Override
    public List<Carro> buscarTodos() {
        return carroRepository.findAll();
    }

    @Override
    public Optional<Carro> buscarPorId(Long id) {
        return carroRepository.findById(id);
    }

    @Override
    public boolean isExistId(Long id) {
        return carroRepository.existsById(id);
    }
}
