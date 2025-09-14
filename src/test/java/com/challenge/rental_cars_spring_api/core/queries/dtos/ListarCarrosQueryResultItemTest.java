package com.challenge.rental_cars_spring_api.core.queries.dtos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.challenge.rental_cars_spring_api.core.domain.Carro;

 class ListarAluguelQueryResultItemTest {

    @Test
     void testFrom() {
        Carro carro = new Carro();
        carro.setId(1L);
        carro.setModelo("Modelo Teste");

        ListarCarrosQueryResultItem dto = ListarCarrosQueryResultItem.from(carro);

        assertEquals(1L, dto.id());
        assertEquals("Modelo Teste", dto.modelo());
    }
}
