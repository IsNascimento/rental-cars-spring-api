package com.challenge.rental_cars_spring_api.access;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.queries.ListarCarrosQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

 class CarrosRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ListarCarrosQuery listarCarrosQuery;

    @InjectMocks
    private CarrosRestController carrosRestController;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carrosRestController).build();
    }

    @Test
     void listarCarros_deveRetornarListaComStatusOk() throws Exception {
        Carro carro1 = new Carro();
        carro1.setId(1L);
        carro1.setModelo("TESTE 1");
     

        Carro carro2 = new Carro();
        carro2.setId(2L);
        carro2.setModelo("TESTE 2");

        List<Carro> listaCarros = Arrays.asList(carro1, carro2);
        when(listarCarrosQuery.execute()).thenReturn(listaCarros);

        mockMvc.perform(get("/carros/listarCarros")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].modelo").value("TESTE 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].modelo").value("TESTE 2"));

        verify(listarCarrosQuery, times(1)).execute();
    }
}
