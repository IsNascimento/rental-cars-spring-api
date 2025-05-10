package com.challenge.rental_cars_spring_api.core.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.queries.ListarAlugueisQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ResultadoImportacaoAluguelDTO;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ResultadoListagemAluguelDTO;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;

@Tag("Unit")
@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class AluguelServiceTest {

    @InjectMocks
    private AluguelService aluguelService;

    @Mock
    private AluguelRepository aluguelRepository;
    @Mock
    private CarroRepository carroRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ListarAlugueisQuery listarAlugueisQuery;

    @Test
    public void processarArquivoTest() {
        Carro carroMock = new Carro();
        carroMock.setId(123L);
        carroMock.setModelo("Modelo Genérico");
        carroMock.setVlrDiaria(new BigDecimal("200"));

        Cliente clienteMock = new Cliente();
        clienteMock.setNome("Cliente Teste");
        clienteMock.setTelefone("+55(11)91234-5678");

        when(carroRepository.findById(3L)).thenReturn(Optional.of(carroMock));
        when(clienteRepository.findById(15L)).thenReturn(Optional.of(clienteMock));
        when(aluguelRepository.save(any())).thenReturn(null);

        MultipartFile arquivo = new MockMultipartFile(
                "alugueis.rtn", "alugueis.rtn", "application/octet-stream",
                "03152024010220240105\n".getBytes(StandardCharsets.UTF_8));

        ResultadoImportacaoAluguelDTO resultado = aluguelService.processarArquivo(arquivo);

        Assertions.assertEquals("alugueis.rtn", resultado.nomeArquivo());
        Assertions.assertEquals(1, resultado.totalRegistros());
        Assertions.assertEquals(0, resultado.totalRegistrosComErro());
        Assertions.assertEquals(1, resultado.totalRegistrosProcessados());
        Mockito.verify(aluguelRepository, Mockito.times(1)).save(Mockito.any(Aluguel.class));
    }

    @Test
    public void consultarAlugueisTest() {
        List<ListarAlugueisQueryResultItem> lista = List.of(
            new ListarAlugueisQueryResultItem(1L, "12/12/2022", "Modelo Genérico", 100, "Cliente Teste", "+55(11)91234-5678", "14/12/2022", "NAO", "1000"),
            new ListarAlugueisQueryResultItem(2L, "12/12/2022", "Modelo Genérico", 100, "Cliente Teste", "+55(11)91234-5678", "14/12/2022", "NAO", "1000"),
            new ListarAlugueisQueryResultItem(3L, "12/12/2022", "Modelo Genérico", 100, "Cliente Teste", "+55(11)91234-5678", "14/12/2022", "SIM", "1000")
        );

        when(listarAlugueisQuery.execute()).thenReturn(lista);

        ResultadoListagemAluguelDTO result = aluguelService.consultarAluguel();

        Assertions.assertEquals(3, result.lista().size());
        Assertions.assertEquals(new BigDecimal("2000"), result.valorTotalNaoPago());
    }
}
