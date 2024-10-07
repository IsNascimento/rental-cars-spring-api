package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.core.queries.ListarCarrosQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarrosRestControllerTest {

    @Mock
    private ListarCarrosQuery listarCarrosQuery;

    @InjectMocks
    private CarrosRestController carrosRestController;

    @Test
    void testeListarCarrosSucesso() {
        when(listarCarrosQuery.execute()).thenReturn(List.of(
                new ListarCarrosQueryResultItem(1L, "", "", 1, 1, "", BigDecimal.ONE)
        ));
        ResponseEntity<List<ListarCarrosQueryResultItem>> response = carrosRestController.listarCarros();
        assertEquals(200, response.getStatusCodeValue());
        verify(listarCarrosQuery).execute();
    }

    @Test
    void testeListarCarrosErroInterno() {
        when(listarCarrosQuery.execute()).thenThrow(new RuntimeException("Erro teste"));
        ResponseEntity<List<ListarCarrosQueryResultItem>> response = carrosRestController.listarCarros();
        assertEquals(500, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
        verify(listarCarrosQuery).execute();
    }
}