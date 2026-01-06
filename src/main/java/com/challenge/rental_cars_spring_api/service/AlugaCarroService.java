package com.challenge.rental_cars_spring_api.service;

import com.challenge.rental_cars_spring_api.core.dtos.ListaResultadoItemCarroDto;
import com.challenge.rental_cars_spring_api.core.dtos.ListarResultadoItemAluguelCarroDto;
import com.challenge.rental_cars_spring_api.core.interfaces.IAlugaCarro;
import com.challenge.rental_cars_spring_api.core.interfaces.ICarro;
import com.challenge.rental_cars_spring_api.core.mapper.AlugueMapper;
import com.challenge.rental_cars_spring_api.core.mapper.CarroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlugaCarroService {

    private final IAlugaCarro alugaCarro;
    private final AlugueMapper aluguelMapper;

    public record Result(List<ListarResultadoItemAluguelCarroDto> alugueis, BigDecimal valorDebito) {}

    public Result execute() {

        var alugueis = alugaCarro.buscarTodos().stream().map(aluguelMapper::toListarResultadoItemAluguelCarro).toList();

        BigDecimal valorTotal = alugaCarro.somarDebito();
        BigDecimal valorDebito = valorTotal != null ? valorTotal : BigDecimal.ZERO;

        return new Result(alugueis, valorDebito);
    }
}
