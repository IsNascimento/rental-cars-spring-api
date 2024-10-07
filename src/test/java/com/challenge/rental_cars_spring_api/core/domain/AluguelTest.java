package com.challenge.rental_cars_spring_api.core.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AluguelTest {

    @Test
    void testAluguelGetters() {
        Carro carro = new Carro();
        Cliente cliente = new Cliente();
        LocalDate dataAluguel = LocalDate.of(2023, 10, 1);
        LocalDate dataDevolucao = LocalDate.of(2023, 10, 10);
        BigDecimal valor = new BigDecimal("500.00");
        Boolean pago = true;

        Aluguel aluguel = new Aluguel(1L, carro, cliente, dataAluguel, dataDevolucao, valor, pago);

        assertEquals(1L, aluguel.getId());
        assertEquals(carro, aluguel.getCarro());
        assertEquals(cliente, aluguel.getCliente());
        assertEquals(dataAluguel, aluguel.getDataAluguel());
        assertEquals(dataDevolucao, aluguel.getDataDevolucao());
        assertEquals(valor, aluguel.getValor());
        assertTrue(aluguel.getPago());
    }

    @Test
    void testAluguelSetters() {
        Aluguel aluguel = new Aluguel();
        Carro carro = new Carro();
        Cliente cliente = new Cliente();
        LocalDate dataAluguel = LocalDate.of(2023, 10, 1);
        LocalDate dataDevolucao = LocalDate.of(2023, 10, 10);
        BigDecimal valor = new BigDecimal("500.00");
        Boolean pago = true;

        aluguel.setId(2L);
        aluguel.setCarro(carro);
        aluguel.setCliente(cliente);
        aluguel.setDataAluguel(dataAluguel);
        aluguel.setDataDevolucao(dataDevolucao);
        aluguel.setValor(valor);
        aluguel.setPago(pago);

        assertEquals(2L, aluguel.getId());
        assertEquals(carro, aluguel.getCarro());
        assertEquals(cliente, aluguel.getCliente());
        assertEquals(dataAluguel, aluguel.getDataAluguel());
        assertEquals(dataDevolucao, aluguel.getDataDevolucao());
        assertEquals(valor, aluguel.getValor());
        assertTrue(aluguel.getPago());
    }
}