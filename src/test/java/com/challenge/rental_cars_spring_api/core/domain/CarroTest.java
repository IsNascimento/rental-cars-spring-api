package com.challenge.rental_cars_spring_api.core.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CarroTest {

    @Test
    void testCarroBuilderAndGetters() {
        Carro carro = new Carro( 1L, "Civic", "2020",5,25000,"Honda", new BigDecimal("199.90")
        );

        assertEquals(1L, carro.getId());
        assertEquals("Civic", carro.getModelo());
        assertEquals("2020", carro.getAno());
        assertEquals(5, carro.getQtdPassageiros());
        assertEquals(25000, carro.getKm());
        assertEquals("Honda", carro.getFabricante());
        assertEquals(new BigDecimal("199.90"), carro.getVlrDiaria());
    }

    @Test
    void testSetters() {
        Carro carro = new Carro();

        carro.setId(2L);
        carro.setModelo("Onix");
        carro.setAno("2022");
        carro.setQtdPassageiros(4);
        carro.setKm(15000);
        carro.setFabricante("Chevrolet");
        carro.setVlrDiaria(new BigDecimal("149.99"));

        assertEquals(2L, carro.getId());
        assertEquals("Onix", carro.getModelo());
        assertEquals("2022", carro.getAno());
        assertEquals(4, carro.getQtdPassageiros());
        assertEquals(15000, carro.getKm());
        assertEquals("Chevrolet", carro.getFabricante());
        assertEquals(new BigDecimal("149.99"), carro.getVlrDiaria());
    }
}
