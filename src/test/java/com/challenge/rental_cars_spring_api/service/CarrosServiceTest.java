package com.challenge.rental_cars_spring_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.dtos.ListaResultadoItemCarroDto;
import com.challenge.rental_cars_spring_api.core.interfaces.ICarro;
import com.challenge.rental_cars_spring_api.core.mapper.CarroMapper;
import com.challenge.rental_cars_spring_api.exception.SistemaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

class CarrosServiceTest {

	 @Mock
	    private ICarro icarro;

	    @Mock
	    private CarroMapper carroMapper;

	    @InjectMocks
	    private CarrosService carrosService;

	    @Test
	    public void testExecuteSucesso() {

	        List<Carro> carrosMock = List.of(new Carro()); // Substitua pela classe correta
	        ListaResultadoItemCarroDto resultadoDtoMock = new ListaResultadoItemCarroDto(
	                "hd20",
	                "2026",
	                5,
	                900,
	                "Hyundai",
	                BigDecimal.valueOf(120.00)
	        );

	        when(icarro.buscarTodos()).thenReturn(carrosMock);
	        when(carroMapper.toListaResultadoItemCarro(any())).thenReturn(resultadoDtoMock);

	        List<ListaResultadoItemCarroDto> result = carrosService.execute();

	        assertEquals(List.of(resultadoDtoMock), result);
	    }

}
