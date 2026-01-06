package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.core.dtos.ListaResultadoItemCarroDto;
import com.challenge.rental_cars_spring_api.service.CarrosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarrosRestControllerTest {

	@InjectMocks
    private CarrosRestController controller;

    @Mock
    private CarrosService service;

    @Test
    public void listarCarrosTest() throws Exception {
     
        var carro1 = new ListaResultadoItemCarroDto(
            "Onix",
            "2025",
            5,
            10000,
            "Chevrolet",
            BigDecimal.valueOf(120.00)
        );

        var carro2 = new ListaResultadoItemCarroDto(
            "CrossFox",
            "2010",
            5,
            10000,
            "Volkwagen",
            BigDecimal.valueOf(46.00)
        );

        List<ListaResultadoItemCarroDto> carrosLis = Arrays.asList(carro1, carro2);

        when(service.execute()).thenReturn((carrosLis));

        var response = controller.listarCarros();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

    }

    @Test
    void listaCarrosVaziaTest() {
        when(service.execute()).thenReturn(List.of());

        var response = controller.listarCarros();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }

}
