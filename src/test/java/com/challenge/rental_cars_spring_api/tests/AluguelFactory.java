package com.challenge.rental_cars_spring_api.tests;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class AluguelFactory {

    public static Aluguel criarAluguel(Boolean pago) {
        Date dataAluguel = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAluguel);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date dataDevolucao = calendar.getTime();

        return new Aluguel(
                1L,
                CarroFactory.criarCarro(),
                ClienteFactory.criarCliente(),
                dataAluguel,
                dataDevolucao,
                BigDecimal.valueOf(200),
                pago
        );
    }

}
