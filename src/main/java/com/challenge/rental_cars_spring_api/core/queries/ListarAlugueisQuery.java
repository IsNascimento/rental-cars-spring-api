package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.core.queries.dtos.RetornoConsultaAlugueis;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarAlugueisQuery {
    private final AluguelRepository aluguelRepository;

    public RetornoConsultaAlugueis execute() {
        List<ListarAlugueisQueryResultItem> alugueis = aluguelRepository.findAll().stream().map(ListarAlugueisQueryResultItem::from).toList();
        BigDecimal result = alugueis
                .stream()
                .filter(p -> p.pago().equals("NAO"))
                .map(ListarAlugueisQueryResultItem::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new RetornoConsultaAlugueis(alugueis, result);
    }
}
