package com.challenge.rental_cars_spring_api.domain.mapper;

import com.challenge.rental_cars_spring_api.domain.Aluguel;
import com.challenge.rental_cars_spring_api.domain.Carro;
import com.challenge.rental_cars_spring_api.domain.Cliente;
import com.challenge.rental_cars_spring_api.domain.dto.AluguelDTO;
import com.challenge.rental_cars_spring_api.domain.enums.EStatusPagamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface AluguelMapper {

    @Mapping(target = "modeloCarro", source = "carro.modelo")
    @Mapping(target = "nomeCliente", source = "cliente.nome")
    @Mapping(target = "telefoneCliente", source = "cliente.telefone")
    @Mapping(target = "statusPagamento", source = "pago", qualifiedByName = "mapStatusPagamento")
    @Mapping(target = "km", source = "carro.km")
    AluguelDTO toDTO(Aluguel aluguel);

    @Named("mapStatusPagamento")
    default EStatusPagamento mapStatusPagamento(boolean pago) {
        return pago ? EStatusPagamento.SIM : EStatusPagamento.NAO;
    }

    default Aluguel criarAluguel(Carro carro, Cliente cliente, LocalDate dataAluguel, LocalDate dataDevolucao) {
        Aluguel aluguel = new Aluguel();
        aluguel.setCarro(carro);
        aluguel.setCliente(cliente);
        aluguel.setDataAluguel(dataAluguel);
        aluguel.setDataDevolucao(dataDevolucao);

        long dias = calcularDiasEntreDatas(dataAluguel, dataDevolucao);
        aluguel.setValor(calcularValorTotal(carro.getVlrDiaria(), dias));
        aluguel.setPago(false);

        return aluguel;
    }

    default long calcularDiasEntreDatas(LocalDate inicio, LocalDate fim) {
        return fim.toEpochDay() - inicio.toEpochDay();
    }

    default BigDecimal calcularValorTotal(BigDecimal valorDiaria, long dias) {
        return valorDiaria.multiply(BigDecimal.valueOf(dias));
    }
}
