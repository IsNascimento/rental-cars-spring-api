package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.exception.ResourceNotFoundException;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarAlugueisQueryTest {

    @Mock
    private AluguelRepository aluguelRepository;

    @InjectMocks
    private ListarAlugueisQuery listarAlugueisQuery;

    @Test
    void testeRetornoVazio() {
        when(aluguelRepository.findAll()).thenReturn(Collections.emptyList());
        List<ListarAlugueisQueryResultItem> result = listarAlugueisQuery.execute();
        assertEquals(0, result.size());
        verify(aluguelRepository, times(1)).findAll();
    }

    @Test
    void testeRetornoPopulado() {
        when(aluguelRepository.findAll()).thenReturn(List.of(new Aluguel(1L, new Carro(), new Cliente(1L, "nome", "000", "111", "999999999"), LocalDate.now(), LocalDate.now(), BigDecimal.ONE, true)));
        List<ListarAlugueisQueryResultItem> result = listarAlugueisQuery.execute();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(aluguelRepository, times(2)).findAll();
    }

    @Test
    void testeCalcularValorTotalNaoPago() {
        when(aluguelRepository.findAll()).thenReturn(List.of(new Aluguel(1L, new Carro(), new Cliente(), LocalDate.now(), LocalDate.now(), BigDecimal.ONE, true)));
        BigDecimal totalNaoPago = listarAlugueisQuery.calcularValorTotalNaoPago();

        assertNotNull(totalNaoPago);
        verify(aluguelRepository, times(1)).findAll();
    }
}