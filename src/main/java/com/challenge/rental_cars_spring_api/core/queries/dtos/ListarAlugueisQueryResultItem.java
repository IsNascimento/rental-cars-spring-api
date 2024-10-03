package com.challenge.rental_cars_spring_api.core.queries.dtos;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;

public record ListarAlugueisQueryResultItem(
        String dataAluguel,
        String modeloCarro,
        Integer kmCarro,
        String nomeCliente,
        String telefoneCliente,
        String dataDevolucao,
        BigDecimal valor,
        String pago) {

    public static ListarAlugueisQueryResultItem from(Aluguel aluguel) {
        return new ListarAlugueisQueryResultItem(
                aluguel.getDataAluguel().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                aluguel.getCarro().getModelo(),
                aluguel.getCarro().getKm(),
                aluguel.getCliente().getNome(),
                formatarTelefone(aluguel.getCliente().getTelefone()),
                aluguel.getDataDevolucao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                aluguel.getValor(),
                aluguel.getPago() ? "SIM" : "NAO");
    }

    private static String formatarTelefone(String telefone) {
        return String.format("+55(%s)%s-%s",
                telefone.substring(0, 2),
                telefone.substring(2, 7),
                telefone.substring(7, 11));
    }
}
