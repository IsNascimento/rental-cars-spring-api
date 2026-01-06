package com.challenge.rental_cars_spring_api.core.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class CarroTest {

	@Test
	public void testNotNull(){
		Carro carro = new Carro();
		carro.setId(1L);
		carro.setModelo("Polo");
		carro.setAno("2020");
		carro.setQtdPassageiros(5);
		carro.setKm(78);
		carro.setFabricante("VW");
		carro.setVlrDiaria(BigDecimal.valueOf(300.00));

		assertNotNull(carro.getId());
		assertNotNull(carro.getModelo());
		assertNotNull(carro.getAno());
		assertNotNull(carro.getQtdPassageiros());
		assertNotNull(carro.getKm());
		assertNotNull(carro.getFabricante());
		assertNotNull(carro.getVlrDiaria());
	}

	@Test
	public void testNull(){
		Carro carro = new Carro();
		carro.setId(null);
		carro.setModelo(null);
		carro.setAno(null);
		carro.setQtdPassageiros(null);
		carro.setKm(null);
		carro.setFabricante(null);
		carro.setVlrDiaria(null);

		assertEquals(carro.getId(),null);
		assertEquals(carro.getModelo(),null);
		assertEquals(carro.getAno(),null);
		assertEquals(carro.getQtdPassageiros(),null);
		assertEquals(carro.getKm(),null);
		assertEquals(carro.getFabricante(),null);
		assertEquals(carro.getVlrDiaria(),null);
	}
}
