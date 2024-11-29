package com.challenge.rental_cars_spring_api.exceptions;

public class ErroAoProcessarArquivoException extends RuntimeException {
    public ErroAoProcessarArquivoException(String message) {
        super(message);
    }
}
