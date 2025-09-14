package com.challenge.rental_cars_spring_api.core.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AluguelTest {

    @Test
    void testAluguelBuilderAndGetters() {
        Carro carro = new Carro();
        carro.setId(1L);
        carro.setModelo("Gol");
        carro.setKm(12000);

        
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João");
        cliente.setTelefone("11912345678");

        LocalDate dataAluguel = LocalDate.of(2023, 1, 1);
        LocalDate dataDevolucao = LocalDate.of(2023, 1, 10);
        BigDecimal valor = new BigDecimal("350.00");

        Aluguel aluguel = new Aluguel(
                1L,
                carro,
                cliente,
                dataAluguel,
                dataDevolucao,
                valor,
                true
        );

        assertEquals(1L, aluguel.getId());
        assertEquals(carro, aluguel.getCarro());
        assertEquals(cliente, aluguel.getCliente());
        assertEquals(dataAluguel, aluguel.getDataAluguel());
        assertEquals(dataDevolucao, aluguel.getDataDevolucao());
        assertEquals(valor, aluguel.getValor());
        assertTrue(aluguel.getPago());
    }

    @Test
    void testSetters() {
        Aluguel aluguel = new Aluguel();

        Carro carro = new Carro();
        carro.setModelo("Fiesta");

        Cliente cliente = new Cliente();
        cliente.setNome("Maria");

        aluguel.setId(2L);
        aluguel.setCarro(carro);
        aluguel.setCliente(cliente);
        aluguel.setDataAluguel(LocalDate.of(2024, 5, 10));
        aluguel.setDataDevolucao(LocalDate.of(2024, 5, 20));
        aluguel.setValor(new BigDecimal("500.00"));
        aluguel.setPago(false);

        assertEquals(2L, aluguel.getId());
        assertEquals("Fiesta", aluguel.getCarro().getModelo());
        assertEquals("Maria", aluguel.getCliente().getNome());
        assertEquals(LocalDate.of(2024, 5, 10), aluguel.getDataAluguel());
        assertEquals(LocalDate.of(2024, 5, 20), aluguel.getDataDevolucao());
        assertEquals(new BigDecimal("500.00"), aluguel.getValor());
        assertFalse(aluguel.getPago());
    }
}
