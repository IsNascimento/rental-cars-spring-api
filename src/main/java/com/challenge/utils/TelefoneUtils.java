package com.challenge.utils;

public class TelefoneUtils {

    public static String formatarTelefone(String telefone) {
        if (telefone == null || telefone.length() != 13) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        return String.format("+%s(%s)%s-%s",
                telefone.substring(0, 2),
                telefone.substring(2, 4),
                telefone.substring(4, 9),
                telefone.substring(9, 13));
    }

}
