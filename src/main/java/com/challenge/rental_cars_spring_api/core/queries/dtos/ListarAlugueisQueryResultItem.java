package com.challenge.rental_cars_spring_api.core.queries.dtos;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ListarAlugueisQueryResultItem(LocalDate dataAluguel, String modeloCarro, Integer kmCarro,
                                            String nomeCliente, String telefoneCliente, LocalDate dataDevolucao,
                                            BigDecimal valor, String pago) {

    public static ListarAlugueisQueryResultItem from(Aluguel aluguel) {
        return new ListarAlugueisQueryResultItem(
                aluguel.getDataAluguel(),
                aluguel.getCarro().getModelo(),
                aluguel.getCarro().getKm(),
                aluguel.getCliente().getNome(),
                formatarTelefone(aluguel.getCliente().getTelefone()),
                aluguel.getDataDevolucao(),
                aluguel.getValor(),
                Boolean.TRUE.equals(aluguel.getPago()) ? "SIM" : "NAO"
        );
    }

    private static String formatarTelefone(String telefone) {
        return "+55(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7);
    }
}