package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ResultadoProcessamentoArquivoDTO;
import com.challenge.rental_cars_spring_api.exceptions.ErroAoProcessarArquivoException;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;
import com.challenge.rental_cars_spring_api.tests.CarroFactory;
import com.challenge.rental_cars_spring_api.tests.ClienteFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Tag("Unit")
@ExtendWith(SpringExtension.class)
public class ProcessarArquivoAluguelQueryTest {

    @InjectMocks
    private ProcessarArquivoAluguelQuery processarArquivoAluguelQuery;

    @Mock
    private AluguelRepository aluguelRepository;

    @Mock
    private CarroRepository carroRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() throws IOException {
        Carro carro = CarroFactory.criarCarro();

        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));

        Cliente cliente = ClienteFactory.criarCliente();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        String conteudoArquivo = "01012024112720241129\n";
        when(multipartFile.getOriginalFilename()).thenReturn("alugueis.rtn");
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(conteudoArquivo.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void processaArquivoDeveProcessarRegistrosCorretamente() {
        ResultadoProcessamentoArquivoDTO resultado = processarArquivoAluguelQuery.processaArquivo(multipartFile);

        Assertions.assertEquals("alugueis.rtn", resultado.getNomeArquivo());
        Assertions.assertEquals(1, resultado.getTotalRegistros());
        Assertions.assertEquals(0, resultado.getTotalRegistrosComErro());
        Assertions.assertEquals(1, resultado.getTotalRegistrosProcessados());
        Mockito.verify(aluguelRepository, Mockito.times(1)).save(Mockito.any(Aluguel.class));
    }

    @Test
    void processaArquivoDeveGerarErroParaLinhaInvalida() throws IOException {
        String conteudoArquivo = "0101";
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(conteudoArquivo.getBytes(StandardCharsets.UTF_8)));

        ResultadoProcessamentoArquivoDTO resultado = processarArquivoAluguelQuery.processaArquivo(multipartFile);

        Assertions.assertEquals("alugueis.rtn", resultado.getNomeArquivo());
        Assertions.assertEquals(1, resultado.getTotalRegistros());
        Assertions.assertEquals(1, resultado.getTotalRegistrosComErro());
        Assertions.assertEquals(0, resultado.getTotalRegistrosProcessados());
        Mockito.verify(aluguelRepository, Mockito.times(0)).save(Mockito.any(Aluguel.class));
    }

    @Test
    void processaArquivoDeveLancarErroAoProcessarArquivoException() throws IOException {
        when(multipartFile.getInputStream()).thenThrow(new RuntimeException("Erro simulado"));

        Assertions.assertThrows(ErroAoProcessarArquivoException.class, () -> {
            processarArquivoAluguelQuery.processaArquivo(multipartFile);
        });

        Mockito.verify(aluguelRepository, Mockito.times(0)).save(Mockito.any(Aluguel.class));
    }
}