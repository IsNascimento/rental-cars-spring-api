package com.challenge.rental_cars_spring_api.infrastructure.handlers;

import com.challenge.rental_cars_spring_api.core.queries.dtos.errors.CustomErrorResponse;
import com.challenge.rental_cars_spring_api.exceptions.FormatoInvalidoDoArquivoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
            {
                    HttpMessageNotReadableException.class,
                    FormatoInvalidoDoArquivoException.class
            })
    public ResponseEntity<CustomErrorResponse> handleBadRequestExceptions(RuntimeException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = buildCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleAllExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        CustomErrorResponse err = buildCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }


    private CustomErrorResponse buildCustomError(HttpStatus status, String message, HttpServletRequest request) {
        return CustomErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(status.value())
                .httpError(status.getReasonPhrase())
                .error(message)
                .path(request.getRequestURI())
                .build();
    }
}
