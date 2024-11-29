package com.challenge.rental_cars_spring_api.exceptions;

public class FormatoInvalidoDoArquivoException extends RuntimeException {
    public FormatoInvalidoDoArquivoException(String extValida, String extEnviada) {
        super(String.format(
                "O arquivo informado é inválido. A extensão do arquivo deve ser '%s' e foi enviada uma extensão: '%s'",
                extValida,
                extEnviada
        ));
    }
}
