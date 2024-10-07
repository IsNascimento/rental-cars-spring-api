package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarCarrosQueryTest {

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private ListarCarrosQuery listarCarrosQuery;

    @Test
    void testeRetornoVazio() {
        when(carroRepository.findAll()).thenReturn(Collections.emptyList());
        List<ListarCarrosQueryResultItem> result = listarCarrosQuery.execute();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(carroRepository, times(1)).findAll();
    }

    @Test
    void testeRetornoPopulado() {
        when(carroRepository.findAll()).thenReturn(List.of(new Carro(1L, "", "", 5, 100, "", BigDecimal.ONE)));
        List<ListarCarrosQueryResultItem> result = listarCarrosQuery.execute();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
