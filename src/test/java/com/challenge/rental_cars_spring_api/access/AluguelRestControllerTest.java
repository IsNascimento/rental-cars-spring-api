package com.challenge.rental_cars_spring_api.access;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.queries.AluguelQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

 class AluguelRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AluguelQuery aluguelService;

    @InjectMocks
    private AluguelRestController aluguelRestController;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(aluguelRestController).build();
    }

    @Test
     void listarCarros_deveRetornarListaComStatusOk() throws Exception {
        Aluguel aluguel = new Aluguel();
        // configure o aluguel se quiser, ex: aluguel.setId(1L);
        
        List<Aluguel> listaAlugueis = Arrays.asList(aluguel);
        when(aluguelService.listarAluguel()).thenReturn(listaAlugueis);

        mockMvc.perform(get("/aluguel/listarAluguel")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());

        verify(aluguelService, times(1)).listarAluguel();
    }

    @Test
    public void processarArquivo_deveRetornarMensagemComStatusOk() throws Exception {
        doNothing().when(aluguelService).processarArquivoRentReport();

        mockMvc.perform(post("/aluguel/processar-arquivo")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Processamento concluído."));

        verify(aluguelService, times(1)).processarArquivoRentReport();
    }
}
