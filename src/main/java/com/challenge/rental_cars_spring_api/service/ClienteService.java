package com.challenge.rental_cars_spring_api.service;

import com.challenge.rental_cars_spring_api.domain.Cliente;

public interface ClienteService {
    Cliente obterClientePorId(Long id);
}
