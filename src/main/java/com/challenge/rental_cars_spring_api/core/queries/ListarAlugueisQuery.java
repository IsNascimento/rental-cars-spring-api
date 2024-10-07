package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListarAlugueisQuery {

    private final AluguelRepository aluguelRepository;

    public List<ListarAlugueisQueryResultItem> execute() {
        if (!aluguelRepository.findAll().isEmpty()) {
            var alugueis = aluguelRepository.findAll()
                    .stream()
                    .map(ListarAlugueisQueryResultItem::from)
                    .collect(Collectors.toList());
            return alugueis;
        } else {
            log.info("Nenhum aluguel encontrado.");
            return new ArrayList<>();
        }
    }

    public BigDecimal calcularValorTotalNaoPago() {
        var total = aluguelRepository.findAll()
                .stream()
                .filter(aluguel -> !aluguel.getPago())
                .map(Aluguel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Valor total não pago: {}", total);
        return total;
    }
}