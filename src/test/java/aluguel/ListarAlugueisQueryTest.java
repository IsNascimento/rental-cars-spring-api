package aluguel;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.queries.ListarAlugueisQuery;
import com.challenge.rental_cars_spring_api.core.queries.ListarCarrosQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.challenge.rental_cars_spring_api.core.queries.dtos.RetornoConsultaAlugueis;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarAlugueisQueryTest {

    @Mock
    private AluguelRepository aluguelRepository;
    @InjectMocks
    private ListarAlugueisQuery listarAlugueisQuery;

    private Aluguel aluguel;
    private Aluguel aluguel1;

    @BeforeEach
    void before() {
        Carro carro = new Carro(1L, "Fusca", "1960", 5, 50000, "Volkswagen", BigDecimal.valueOf(100));
        Cliente cliente = new Cliente(1L, "João Silva", "43374922759", "29677677708", "51999999999");
        aluguel = new Aluguel(1L,
                carro,
                cliente, LocalDate.of(2024, 1, 2),
                LocalDate.of(2024, 1, 5),
                BigDecimal.valueOf(100),
                true);

        aluguel1 = new Aluguel(2L,
                carro,
                cliente, LocalDate.of(2024, 1, 2),
                LocalDate.of(2024, 1, 5),
                BigDecimal.valueOf(200),
                false);

    }

    @Test
    void execute() {

        List<Aluguel> alugueis = Arrays.asList(aluguel, aluguel1);

        when(aluguelRepository.findAll()).thenReturn(alugueis);

        RetornoConsultaAlugueis execute = listarAlugueisQuery.execute();

        Assertions.assertThat(execute).isNotNull();
        Assertions.assertThat(execute.alugueis().size()).isEqualTo(2);

        BigDecimal result = alugueis
                .stream()
                .filter(a -> !a.isPago())
                .map(Aluguel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Assertions.assertThat(execute.valorNaoPago()).isEqualByComparingTo(result);
    }

}
