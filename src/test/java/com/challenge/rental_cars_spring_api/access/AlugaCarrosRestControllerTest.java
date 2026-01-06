package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.core.dtos.ListarResultadoItemAluguelCarroDto;
import com.challenge.rental_cars_spring_api.service.AlugaCarroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlugaCarrosRestControllerTest {

	    @InjectMocks
	    private AlugaCarrosRestController controller;

	    @Mock
	    private AlugaCarroService service;



	    @Test
	    void listarAlugueisTest() {
	        var aluguel = new ListarResultadoItemAluguelCarroDto(
	                LocalDate.now(),
	                "Polo",
	                10000,
	                "Jose Bonifacio de Alcantra e Silva",
	                "(45) 99999-9999",
	                LocalDate.now().plusDays(5),
	                BigDecimal.valueOf(500.00),
	                "NAO"
	        );

	        var resultado = new AlugaCarroService.Result(List.of(aluguel), BigDecimal.valueOf(500.00));
	        when(service.execute()).thenReturn(resultado);

	        var response = controller.listarAlugueis();

	        assertNotNull(response);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	        assertEquals(1, response.getBody().alugueis().size());
	        assertEquals(BigDecimal.valueOf(500.00), response.getBody().valorDebito());
	    }

	    @Test
	    void listarAlugueisVazioTest() {

	        var resultadoVazio = new AlugaCarroService.Result(List.of(), BigDecimal.valueOf(0.00));

	        when(service.execute()).thenReturn(resultadoVazio);

	        var response = controller.listarAlugueis();
	        assertNotNull(response);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	        assertEquals(0, response.getBody().alugueis().size());
	        assertEquals(BigDecimal.valueOf(0.00), response.getBody().valorDebito());
	    }

}
