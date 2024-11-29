package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarCarrosQuery {

    private final CarroRepository carroRepository;

    @Transactional(readOnly = true)
    public List<ListarCarrosQueryResultItem> execute() {
        return carroRepository
                .findAll()
                .stream()
                .map(ListarCarrosQueryResultItem::from)
                .toList();
    }
}
