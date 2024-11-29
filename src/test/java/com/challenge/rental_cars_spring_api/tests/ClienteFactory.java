package com.challenge.rental_cars_spring_api.tests;

import com.challenge.rental_cars_spring_api.core.domain.Cliente;

public class ClienteFactory {

    public static Cliente criarCliente() {
        return new Cliente(
                1L,
                "John Doe",
                "12345678998",
                "11111111111",
                "11999999999"
        );
    }
}
