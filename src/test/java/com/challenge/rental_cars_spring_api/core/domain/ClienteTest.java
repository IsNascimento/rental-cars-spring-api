package com.challenge.rental_cars_spring_api.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testClienteConstructorAndGetters() {
        Cliente cliente = new Cliente(
                1L,
                "João Silva",
                "12345678900",
                "98765432100",
                "11912345678"
        );

        assertEquals(1L, cliente.getId());
        assertEquals("João Silva", cliente.getNome());
        assertEquals("12345678900", cliente.getCpf());
        assertEquals("98765432100", cliente.getCnh());
        assertEquals("11912345678", cliente.getTelefone());
    }

    @Test
    void testSetters() {
        Cliente cliente = new Cliente();

        cliente.setId(2L);
        cliente.setNome("Maria Souza");
        cliente.setCpf("11122233344");
        cliente.setCnh("44433322211");
        cliente.setTelefone("1199876-5432");

        assertEquals(2L, cliente.getId());
        assertEquals("Maria Souza", cliente.getNome());
        assertEquals("11122233344", cliente.getCpf());
        assertEquals("44433322211", cliente.getCnh());
        assertEquals("1199876-5432", cliente.getTelefone());
    }
}
