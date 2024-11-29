package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.queries.dtos.AluguelResponseDTO;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.tests.AluguelFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@Tag("Unit")
@ExtendWith(SpringExtension.class)
public class ListarAlugueisQueryTest {

    @InjectMocks
    private ListarAlugueisQuery listarAlugueisQuery;

    @Mock
    private AluguelRepository aluguelRepository;

    @BeforeEach
    void setUp() {
        Aluguel aluguelPago = AluguelFactory.criarAluguel(true);
        Aluguel aluguelNaoPago = AluguelFactory.criarAluguel(false);

        when(aluguelRepository.findAll()).thenReturn(List.of(aluguelPago, aluguelNaoPago));
    }

    @Test
    void listarAlugueisDeveRetornarOValorCorretoParaNaoPagos() {
        AluguelResponseDTO result = listarAlugueisQuery.execute();

        Mockito.verify(aluguelRepository, Mockito.times(1)).findAll();
        Assertions.assertEquals(2, result.alugueis().size());
        Assertions.assertEquals(BigDecimal.valueOf(200), result.totalNaoPago());
    }

}
