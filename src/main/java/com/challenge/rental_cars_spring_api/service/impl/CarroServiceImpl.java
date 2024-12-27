package com.challenge.rental_cars_spring_api.service.impl;

import com.challenge.rental_cars_spring_api.domain.Carro;
import com.challenge.rental_cars_spring_api.domain.dto.ListarCarrosResponse;
import com.challenge.rental_cars_spring_api.exception.ResourceNotFoundException;
import com.challenge.rental_cars_spring_api.repository.CarroRepository;
import com.challenge.rental_cars_spring_api.service.CarroService;
import com.challenge.rental_cars_spring_api.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarroServiceImpl implements CarroService {

    private static final Logger logger = LoggerFactory.getLogger(CarroServiceImpl.class);
    private final CarroRepository carroRepository;
    private final MessageUtil messageUtil;

    @Override
    public List<ListarCarrosResponse> listarCarros() {
        logger.info("Buscando todos os carros.");
        List<ListarCarrosResponse> carros = carroRepository.findAll().stream()
                .map(ListarCarrosResponse::from)
                .collect(Collectors.toList());

        if (carros.isEmpty()) {
            logger.warn("Nenhum carro encontrado.");
            throw new ResourceNotFoundException(messageUtil.getMessage("carro.not-found"));
        }

        return carros;
    }

    @Override
    public Carro obterCarroPorId(Long id) {
        logger.info("Buscando carro com ID: {}", id);
        return carroRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Carro não encontrado para o ID: {}", id);
                    return new ResourceNotFoundException(messageUtil.getMessage("carro.not-found", id));
                });
    }

}
