package com.challenge.rental_cars_spring_api.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteTest {

    @Test
    void testClienteGetters() {
        Long id = 1L;
        String nome = "teste";
        String cpf = "cpfteste";
        String cnh = "cnhteste";
        String telefone = "telefoneteste";

        Cliente cliente = new Cliente(id, nome, cpf, cnh, telefone);

        assertEquals(id, cliente.getId());
        assertEquals(nome, cliente.getNome());
        assertEquals(cpf, cliente.getCpf());
        assertEquals(cnh, cliente.getCnh());
        assertEquals(telefone, cliente.getTelefone());
    }
}