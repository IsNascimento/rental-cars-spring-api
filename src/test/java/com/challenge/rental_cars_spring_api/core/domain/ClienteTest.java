package com.challenge.rental_cars_spring_api.core.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteTest {

	@Test
	public void testNotNull() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNome("Mauricio Neto");
		cliente.setCpf("1234567890");
		cliente.setCnh("1234567890");
		cliente.setTelefone("1234567890");

		assertNotNull(cliente);
	}

	@Test
	public void testNull(){
		Cliente cliente = new Cliente();
		cliente.setId(null);
		cliente.setNome(null);
		cliente.setCpf(null);
		cliente.setCnh(null);
		cliente.setTelefone(null);

		assertEquals(cliente.getId(),null);
		assertEquals(cliente.getNome(),null);
		assertEquals(cliente.getCpf() ,null);
		assertEquals(cliente.getCnh(),null);
		assertEquals(cliente.getTelefone(),null);
	}

}
