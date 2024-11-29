package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.tests.CarroFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@Tag("Unit")
@ExtendWith(SpringExtension.class)
public class ListarCarrosQueryTest {

    @InjectMocks
    private ListarCarrosQuery listarCarrosQuery;

    @Mock
    private CarroRepository carroRepository;


    @BeforeEach
    void setUp() {
        Mockito.when(carroRepository.findAll()).thenReturn(List.of(CarroFactory.criarCarro()));
    }

    @Test
    void listarCarrosDeveRetornarUmaLista() {
        Carro carro = CarroFactory.criarCarro();

        List<Carro> carros = List.of(carro);

        when(carroRepository.findAll()).thenReturn(carros);

        List<ListarCarrosQueryResultItem> result = listarCarrosQuery.execute();

        Assertions.assertEquals(1, result.size());
        Mockito.verify(carroRepository, Mockito.times(1)).findAll();
    }

}
