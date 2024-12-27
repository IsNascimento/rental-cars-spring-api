package com.challenge.rental_cars_spring_api.util;

import com.challenge.rental_cars_spring_api.exception.BusinessException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AluguelParseUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static Long parseLong(String valor, String campo, MessageUtil messageUtil) {
        try {
            return Long.parseLong(valor.trim());
        } catch (NumberFormatException e) {
            throw new BusinessException(messageUtil.getMessage("erro.conversao", campo));
        }
    }

    public static LocalDate parseData(String valor, String campo, MessageUtil messageUtil) {
        try {
            return LocalDate.parse(valor.trim(), FORMATTER);
        } catch (Exception e) {
            throw new BusinessException(messageUtil.getMessage("erro.data.invalida", campo));
        }
    }
}
