package aluguel;

import com.challenge.rental_cars_spring_api.access.AluguelRestController;
import com.challenge.rental_cars_spring_api.core.queries.ListarAlugueisQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.core.queries.dtos.RetornoConsultaAlugueis;
import com.challenge.rental_cars_spring_api.core.service.UploadAluguelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AluguelRestControllerTest {

    @Mock
    private ListarAlugueisQuery listarAlugueisQuery;
    @Mock
    private UploadAluguelService uploadAluguelService;
    @InjectMocks
    private AluguelRestController aluguelRestController;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private RetornoConsultaAlugueis retornoConsultaAlugueis;
    private ListarAlugueisQueryResultItem listarAlugueisQueryResultItem;
    private MockMultipartFile multipartFile;

    @BeforeEach
    void before() {
        final LocalDate dateInit = LocalDate.of(2025, 1, 1);
        final LocalDate dateFinal = LocalDate.of(2025, 1, 5);
        objectMapper.registerModule(new JavaTimeModule());

        listarAlugueisQueryResultItem = new ListarAlugueisQueryResultItem(
                dateInit,
                "Fusca",
                1000,
                "João Silva",
                "51999999999",
                dateFinal,
                BigDecimal.valueOf(100),
                "NAO"
        );

        retornoConsultaAlugueis = new RetornoConsultaAlugueis(Collections.singletonList(listarAlugueisQueryResultItem), BigDecimal.valueOf(100));

        String conteudo = "03152024010220240105";

        multipartFile = new MockMultipartFile(
                "file",
                "file.rtn",
                "text/plain",
                conteudo.getBytes(StandardCharsets.UTF_8)
        );
        mockMvc = MockMvcBuilders.standaloneSetup(aluguelRestController).build();
    }

    @Test
    @SneakyThrows
    void uploadAlugueis() {

        mockMvc.perform(multipart("/aluguel/upload")
                        .file(multipartFile))
                        .andExpect(status().isOk());

    }

    @Test
    @SneakyThrows
    void listarAlugueis() {
        when(listarAlugueisQuery.execute()).thenReturn(retornoConsultaAlugueis);

        mockMvc.perform(get("/aluguel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(retornoConsultaAlugueis)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alugueis", hasSize(1)))
                .andExpect(jsonPath("$.valorNaoPago").value(retornoConsultaAlugueis.valorNaoPago().toString()));

        verify(listarAlugueisQuery, times(1)).execute();

    }


}
