package com.challenge.rental_cars_spring_api.core.queries.dtos;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;

import java.text.SimpleDateFormat;
import java.util.Locale;

public record ListarAlugueisQueryResultItem(
        Long id,
        String dataAluguel,
        String modelo,
        Integer km,
        String cliente,
        String telefone,
        String dataDevolucao,
        String pago,
        String valor
) {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private static String formatarTelefone(String telefone) {
        if (telefone == null || telefone.length() < 11) return telefone;
        return "+55 (" + telefone.substring(0, 2) + ") " +
               telefone.substring(2, 7) + "-" +
               telefone.substring(7);
    }

    public static ListarAlugueisQueryResultItem from(Aluguel aluguel) {
        return new ListarAlugueisQueryResultItem(
                aluguel.getId(),
                DATE_FORMATTER.format(aluguel.getDataAluguel()),
                aluguel.getCarro().getModelo(),
                aluguel.getCarro().getKm(),
                aluguel.getCliente().getNome(),
                formatarTelefone(aluguel.getCliente().getTelefone()),
                DATE_FORMATTER.format(aluguel.getDataDevolucao()),
                Boolean.TRUE.equals(aluguel.getPago()) ? "SIM" : "NAO",
                aluguel.getValor().toPlainString()
        );
    }
}
