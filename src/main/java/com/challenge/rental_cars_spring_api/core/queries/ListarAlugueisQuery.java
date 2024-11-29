package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.queries.dtos.AluguelItemResponseDTO;
import com.challenge.rental_cars_spring_api.core.queries.dtos.AluguelResponseDTO;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarAlugueisQuery {

    private final AluguelRepository aluguelRepository;

    @Transactional(readOnly = true)
    public AluguelResponseDTO execute() {
        List<AluguelItemResponseDTO> alugueis = aluguelRepository.findAll()
                .stream()
                .map(AluguelItemResponseDTO::from)
                .toList();
        return AluguelResponseDTO.from(alugueis);
    }

}
