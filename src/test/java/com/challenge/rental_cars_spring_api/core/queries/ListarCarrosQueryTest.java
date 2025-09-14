package com.challenge.rental_cars_spring_api.core.queries;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;

@ExtendWith(MockitoExtension.class)
public class ListarCarrosQueryTest {

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private ListarCarrosQuery listarCarrosQuery;

    @Test
     void testExecute_RetornaListaDeCarros() {
        Carro carro1 = new Carro(1L, "TESTE 1", "2020", 4, 10000, "Fabricante 1", null);
        Carro carro2 = new Carro(2L, "TESTE 2", "2021", 5, 5000, "Fabricante 2", null);

        when(carroRepository.findAll()).thenReturn(Arrays.asList(carro1, carro2));

        List<Carro> resultado = listarCarrosQuery.execute();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("TESTE 1", resultado.get(0).getModelo());
        assertEquals("TESTE 2", resultado.get(1).getModelo());

        verify(carroRepository, times(1)).findAll();
    }
}
