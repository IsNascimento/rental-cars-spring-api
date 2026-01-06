package com.challenge.rental_cars_spring_api.core.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class AlugaCarroTest {

	@Test
	public void testNotNull(){
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		Carro carro = new Carro();
		carro.setId(1L);
		AlugaCarro alugaCarro = new AlugaCarro();
		alugaCarro.setId(1L);
		alugaCarro.setCarro(carro);
		alugaCarro.setCliente(cliente);
		alugaCarro.setDataAluguel(LocalDate.of(2026, 1, 1));
		alugaCarro.setDataDevolucao(LocalDate.of(2026, 1, 3));
		alugaCarro.setValor(BigDecimal.valueOf(300.00));
		alugaCarro.setPago(true);

		assertNotNull(alugaCarro.getId());
		assertNotNull(alugaCarro.getCarro());
		assertNotNull(alugaCarro.getCliente());
		assertNotNull(alugaCarro.getDataAluguel());
		assertNotNull(alugaCarro.getDataDevolucao());
		assertNotNull(alugaCarro.getValor());
		assertNotNull(alugaCarro.isPago());
	}

	@Test
	public void testNull(){
		AlugaCarro alugaCarro = new AlugaCarro();
		alugaCarro.setId(null);
		alugaCarro.setCarro(null);
		alugaCarro.setCliente(null);
		alugaCarro.setDataAluguel(null);
		alugaCarro.setDataDevolucao(null);
		alugaCarro.setValor(null);

		assertEquals(alugaCarro.getId(),null);
		assertEquals(alugaCarro.getCarro(),null);
		assertEquals(alugaCarro.getCliente(),null);
		assertEquals(alugaCarro.getDataAluguel(),null);
		assertEquals(alugaCarro.getDataDevolucao(),null);
		assertEquals(alugaCarro.getValor(),null);

	}

}
