package com.challenge.rental_cars_spring_api.infrastructure.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest httpServletRequest;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDto> handleException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ExceptionUtils.getRootCauseMessage(ex);
        ErrorResponseDto errorResponse = new ErrorResponseDto(status.value(), message, ex.getMessage());

        log.error("Ocorreu o erro: {}", ex.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

}
