package carro;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.queries.ListarCarrosQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarCarrosQueryTest {
    @Mock
    private CarroRepository carroRepository;
    @InjectMocks
    private ListarCarrosQuery listarCarrosQuery;

    private Carro carro;

    @BeforeEach
    void before() {
        carro = new Carro(1L, "Fusca", "1960", 5, 50000, "Volkswagen", BigDecimal.valueOf(100));

    }

    @Test
    void execute() {
        when(carroRepository.findAll()).thenReturn(Collections.singletonList(carro));

        List<ListarCarrosQueryResultItem> execute = listarCarrosQuery.execute();
        Assertions.assertThat(execute).isNotEmpty();
        Assertions.assertThat(execute.size()).isEqualTo(1);

        ListarCarrosQueryResultItem item = execute.get(0);
        Assertions.assertThat(item.id()).isEqualTo(carro.getId());
        Assertions.assertThat(item.modelo()).isEqualTo(carro.getModelo());
    }
}
