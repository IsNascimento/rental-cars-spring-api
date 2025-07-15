package aluguel;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.service.UploadAluguelService;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UploadAluguelServiceTest {

    @Mock
    private AluguelRepository aluguelRepository;
    @Mock
    private CarroRepository carroRepository;
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private UploadAluguelService uploadAluguelService;

    private MockMultipartFile multipartFile;
    private Carro carro;
    private Cliente cliente;
    private Aluguel aluguel;

    @BeforeEach
    void before() {
        String conteudo = "03152024010220240105";

        multipartFile = new MockMultipartFile(
                "file",
                "file.rtn",
                "text/plain",
                conteudo.getBytes(StandardCharsets.UTF_8)
        );

        carro = new Carro(1L, "Fusca", "1960", 5, 50000, "Volkswagen", BigDecimal.valueOf(100));
        cliente = new Cliente(1L, "João Silva", "43374922759", "29677677708", "51999999999");
        aluguel = new Aluguel(1L,
                carro,
                cliente, LocalDate.of(2024, 1, 2),
                LocalDate.of(2024, 1, 5),
                BigDecimal.valueOf(100),
                false);
    }

    @SneakyThrows
    @Test
    void execute() {
        when(carroRepository.findById(3L)).thenReturn(Optional.of(carro));
        when(clienteRepository.findById(15L)).thenReturn(Optional.of(cliente));
        when(aluguelRepository.save(any(Aluguel.class))).thenReturn(aluguel);

        assertDoesNotThrow(() -> uploadAluguelService.execute(multipartFile));
    }

    @SneakyThrows
    @Test
    void executeCarroNaoEncontrato() {
        when(carroRepository.findById(3L)).thenReturn(Optional.empty());
        when(clienteRepository.findById(15L)).thenReturn(Optional.of(cliente));

        assertDoesNotThrow(() -> uploadAluguelService.execute(multipartFile));
    }

    @SneakyThrows
    @Test
    void executeClienteNaoEncontrato() {
        when(carroRepository.findById(3L)).thenReturn(Optional.of(carro));
        when(clienteRepository.findById(15L)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> uploadAluguelService.execute(multipartFile));
    }

}
