package com.challenge.rental_cars_spring_api.core.queries;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.queries.dtos.AlugueisResponse;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarAlugueisQuery {

    private final AluguelRepository aluguelRepository;

    public AlugueisResponse execute() {
        List<ListarAlugueisQueryResultItem> alugueis = aluguelRepository.findAll().stream()
                .map(ListarAlugueisQueryResultItem::from)
                .toList();
        BigDecimal valorTotalNaoPago = calcularValorTotalNaoPago();

        return new AlugueisResponse(alugueis, valorTotalNaoPago);
    }

    public BigDecimal calcularValorTotalNaoPago() {
        return aluguelRepository.findAll().stream()
                .filter(aluguel -> !aluguel.getPago())
                .map(Aluguel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
