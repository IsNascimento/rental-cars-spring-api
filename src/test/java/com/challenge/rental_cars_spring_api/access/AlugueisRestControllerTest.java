package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.core.queries.ListarAlugueisQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlugueisRestControllerTest {

    @Mock
    private ListarAlugueisQuery listarAlugueisQuery;

    @Mock
    private ListarAlugueisQueryResultItem listarAlugueisQueryResultItem;

    @InjectMocks
    private AlugueisRestController alugueisRestController;

    @Test
    void testeListarAlugueis() {

        var alugueis = List.of(
                new ListarAlugueisQueryResultItem(LocalDate.now(), "modeloTeste", 100, "nome", "telefone", LocalDate.now(), BigDecimal.ONE, "pago")
        );
        var mockValorTotalNaoPago = new BigDecimal("100.00");

        when(listarAlugueisQuery.execute()).thenReturn(alugueis);
        when(listarAlugueisQuery.calcularValorTotalNaoPago()).thenReturn(mockValorTotalNaoPago);

        ResponseEntity<Map<String, Object>> response = alugueisRestController.listarAlugueis();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(alugueis, response.getBody().get("alugueis"));
        assertEquals(mockValorTotalNaoPago, response.getBody().get("valorTotalNaoPago"));

        verify(listarAlugueisQuery).execute();
        verify(listarAlugueisQuery).calcularValorTotalNaoPago();
    }
}