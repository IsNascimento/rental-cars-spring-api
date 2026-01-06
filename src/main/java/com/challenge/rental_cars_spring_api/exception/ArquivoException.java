package com.challenge.rental_cars_spring_api.exception;

import java.io.Serializable;

public class ArquivoException extends RuntimeException {

    public ArquivoException(String message) {
        super(message);
    }

    public ArquivoException(String message, Throwable cause) {
        super(message, cause);
    }
}
