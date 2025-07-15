package com.challenge.rental_cars_spring_api.core.queries.dtos;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.utils.PhoneNumberFormatter;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ListarAlugueisQueryResultItem(LocalDate dataAluguel,
                                            String modeloCarro,
                                            Integer kmCarro,
                                            String nomeCliente,
                                            String telefoneCliente,
                                            LocalDate dataDevolucao,
                                            BigDecimal valor,
                                            String pago) {

    public static ListarAlugueisQueryResultItem from(Aluguel aluguel) {
        return new ListarAlugueisQueryResultItem(
                aluguel.getDataAluguel(),
                aluguel.getCarro().getModelo(),
                aluguel.getCarro().getKm(),
                aluguel.getCliente().getNome(),
                PhoneNumberFormatter.formatPhoneNumber(aluguel.getCliente().getTelefone()),
                aluguel.getDataDevolucao(),
                aluguel.getValor(),
                aluguel.isPago() ? "SIM" : "NAO"
        );
    }
}
