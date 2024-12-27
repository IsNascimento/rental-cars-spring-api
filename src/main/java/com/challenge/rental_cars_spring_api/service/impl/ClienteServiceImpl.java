package com.challenge.rental_cars_spring_api.service.impl;

import com.challenge.rental_cars_spring_api.domain.Cliente;
import com.challenge.rental_cars_spring_api.exception.ResourceNotFoundException;
import com.challenge.rental_cars_spring_api.repository.ClienteRepository;
import com.challenge.rental_cars_spring_api.service.ClienteService;
import com.challenge.rental_cars_spring_api.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);
    private final ClienteRepository clienteRepository;
    private final MessageUtil messageUtil;

    @Override
    public Cliente obterClientePorId(Long id) {
        logger.info("Buscando cliente com ID: {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cliente não encontrado para o ID: {}", id);
                    return new ResourceNotFoundException(messageUtil.getMessage("cliente.not-found", id));
                });
    }

}
