package com.challenge.rental_cars_spring_api.exception;

import java.io.Serializable;

public class SistemaException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String codigoErro;

     public SistemaException(String mensagem, String codigoErro) {
        this.codigoErro = codigoErro;
    }

    public String getCodigoErro() {
        return codigoErro;
    }
}
