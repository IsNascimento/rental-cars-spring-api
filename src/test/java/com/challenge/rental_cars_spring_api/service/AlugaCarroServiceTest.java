package com.challenge.rental_cars_spring_api.service;

import com.challenge.rental_cars_spring_api.core.domain.AlugaCarro;
import com.challenge.rental_cars_spring_api.core.dtos.ListarResultadoItemAluguelCarroDto;
import com.challenge.rental_cars_spring_api.core.interfaces.IAlugaCarro;
import com.challenge.rental_cars_spring_api.core.mapper.AlugueMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AlugaCarroServiceTest {

	@Mock
    private IAlugaCarro alugaCarro;

    @Mock
    private AlugueMapper aluguelMapper;

    @InjectMocks
    private AlugaCarroService alugaCarroService;

    private AlugaCarro aluguelCarro;

    @Test
    public void testExecuteSucesso() {

        List<AlugaCarro> alugueisMock = List.of(new AlugaCarro());
        List<ListarResultadoItemAluguelCarroDto> resultadoDtoMock =
                List.of(new ListarResultadoItemAluguelCarroDto(
                        LocalDate.of(2026, 1, 1),
                        "Onix",
                        1000,
                        "Jose Bonifacio de Alcantra e Silva",
                        "01478523690",
                        LocalDate.of(2026, 1, 5),
                        BigDecimal.valueOf(400.00),
                        "true"));

        BigDecimal valorTotalMock = BigDecimal.valueOf(100.00);

        when(alugaCarro.buscarTodos()).thenReturn(alugueisMock);
        when(aluguelMapper.toListarResultadoItemAluguelCarro(any())).thenReturn(resultadoDtoMock.get(0));
        when(alugaCarro.somarDebito()).thenReturn(valorTotalMock);

        AlugaCarroService.Result result = alugaCarroService.execute();

        assertEquals(resultadoDtoMock, result.alugueis());
        assertEquals(valorTotalMock, result.valorDebito());
    }

    @Test
    public void testExecuteInsucesso() {

        List<AlugaCarro> alugueisMock = List.of(new AlugaCarro());
        ListarResultadoItemAluguelCarroDto resultadoDtoMock = new ListarResultadoItemAluguelCarroDto(
                LocalDate.now(),
                "Fox",
                1000,
                "Pedro Alvares",
                "45999999999",
                LocalDate.now().plusDays(5),
                BigDecimal.valueOf(100.00),
                "Ativo"
        );

        when(alugaCarro.buscarTodos()).thenReturn(alugueisMock);
        when(aluguelMapper.toListarResultadoItemAluguelCarro(any())).thenReturn(resultadoDtoMock);
        when(alugaCarro.somarDebito()).thenReturn(null);

        AlugaCarroService.Result result = alugaCarroService.execute();

        assertEquals(List.of(resultadoDtoMock), result.alugueis());
        assertEquals(BigDecimal.ZERO, result.valorDebito());
    }

}
