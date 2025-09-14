package com.challenge.rental_cars_spring_api.core.queries;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;

@ExtendWith(MockitoExtension.class)
 class AluguelQueryTest {

    @Mock
    private AluguelRepository aluguelRepository;

    @Mock
    private CarroRepository carroRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private AluguelQuery aluguelQuery;
    @Test
    public void testProcessarArquivoRentReport_SalvaAluguelComSucesso() throws Exception {
        Carro carroMock = new Carro();
        carroMock.setId(1L);
        carroMock.setVlrDiaria(new BigDecimal("100.00"));

        Cliente clienteMock = new Cliente();
        clienteMock.setId(1L);

        when(carroRepository.findById(1L)).thenReturn(Optional.of(carroMock));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));

        
        aluguelQuery.processarArquivoRentReport();

        ArgumentCaptor<Aluguel> aluguelCaptor = ArgumentCaptor.forClass(Aluguel.class);
        
    }

}
