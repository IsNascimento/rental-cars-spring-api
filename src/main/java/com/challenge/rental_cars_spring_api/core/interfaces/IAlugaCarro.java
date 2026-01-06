package com.challenge.rental_cars_spring_api.core.interfaces;

import com.challenge.rental_cars_spring_api.core.domain.AlugaCarro;

import java.math.BigDecimal;
import java.util.List;

public interface IAlugaCarro {
    List<AlugaCarro> buscarTodos();
    BigDecimal somarDebito();
    AlugaCarro salvar(AlugaCarro aluguel);
}
