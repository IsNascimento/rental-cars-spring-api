package com.challenge.rental_cars_spring_api.core.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarroTest {

    @Test
    void testCarroGetters() {
        Long id = 1L;
        String modelo = "modelo";
        String ano = "2023";
        Integer qtdPassageiros = 5;
        Integer km = 10000;
        String fabricante = "fabricante";
        BigDecimal vlrDiaria = new BigDecimal("150.00");

        Carro carro = new Carro(id, modelo, ano, qtdPassageiros, km, fabricante, vlrDiaria);

        assertEquals(id, carro.getId());
        assertEquals(modelo, carro.getModelo());
        assertEquals(ano, carro.getAno());
        assertEquals(qtdPassageiros, carro.getQtdPassageiros());
        assertEquals(km, carro.getKm());
        assertEquals(fabricante, carro.getFabricante());
        assertEquals(vlrDiaria, carro.getVlrDiaria());
    }

    @Test
    void testCarroSetters() {
        Carro carro = new Carro();
        Long id = 2L;
        String modelo = "modelo";
        String ano = "2022";
        Integer qtdPassageiros = 4;
        Integer km = 15000;
        String fabricante = "fabricante";
        BigDecimal vlrDiaria = new BigDecimal("200.00");

        carro.setId(id);
        carro.setModelo(modelo);
        carro.setAno(ano);
        carro.setQtdPassageiros(qtdPassageiros);
        carro.setKm(km);
        carro.setFabricante(fabricante);
        carro.setVlrDiaria(vlrDiaria);

        assertEquals(id, carro.getId());
        assertEquals(modelo, carro.getModelo());
        assertEquals(ano, carro.getAno());
        assertEquals(qtdPassageiros, carro.getQtdPassageiros());
        assertEquals(km, carro.getKm());
        assertEquals(fabricante, carro.getFabricante());
        assertEquals(vlrDiaria, carro.getVlrDiaria());
    }
}