package com.challenge.rental_cars_spring_api.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@Slf4j
public class Format {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String formatarTelefone(String telefone) {
        if (telefone == null || telefone.length() != 11) {
            return telefone;
        }
        return String.format("(%s) %s-%s", telefone.substring(0, 2), telefone.substring(2, 7), telefone.substring(7));
    }

    public static int calcularDias(String strDataInicial, String strDataFinal) {
        try {
            LocalDate inicio = LocalDate.parse(strDataInicial, DATE_FORMATTER);
            LocalDate fim = LocalDate.parse(strDataFinal, DATE_FORMATTER);

            return (int) java.time.temporal.ChronoUnit.DAYS.between(inicio, fim);

        } catch (DateTimeParseException e) {
            log.error("Formato de data inválido: ", e.getMessage());
            return 0;
        }
    }
}
