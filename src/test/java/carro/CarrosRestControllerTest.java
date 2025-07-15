package carro;

import com.challenge.rental_cars_spring_api.access.CarrosRestController;
import com.challenge.rental_cars_spring_api.core.queries.ListarCarrosQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class CarrosRestControllerTest {

    @Mock
    private ListarCarrosQuery listarCarrosQuery;
    @InjectMocks
    private CarrosRestController carrosRestController;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ListarCarrosQueryResultItem listarCarrosQueryResultItem;

    @BeforeEach
    void before() {
        listarCarrosQueryResultItem = new ListarCarrosQueryResultItem(1L, "Fusca");
        mockMvc = MockMvcBuilders.standaloneSetup(carrosRestController).build();
    }

    @Test
    @SneakyThrows
    void listarCarros() {
        when(listarCarrosQuery.execute()).thenReturn(Collections.singletonList(listarCarrosQueryResultItem));

        mockMvc.perform(get("/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(Collections.singletonList(listarCarrosQueryResultItem))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(listarCarrosQueryResultItem.id()))
                .andExpect(jsonPath("$[0].modelo").value(listarCarrosQueryResultItem.modelo()));

        verify(listarCarrosQuery, times(1)).execute();
    }


}
