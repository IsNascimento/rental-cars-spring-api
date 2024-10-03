package com.challenge.rental_cars_spring_api.core.queries;

import java.util.List;

import org.springframework.stereotype.Service;

import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarCarrosQuery {

    private final CarroRepository carroRepository;

    public List<ListarCarrosQueryResultItem> execute() {
        return carroRepository.findAll().stream()
                .map(ListarCarrosQueryResultItem::from)
                .toList();
    }
}
