package com.challenge.rental_cars_spring_api.repositories;

import com.challenge.rental_cars_spring_api.core.domain.AlugaCarro;
import com.challenge.rental_cars_spring_api.core.interfaces.IAlugaCarro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AlugaCarroRepositoryImp implements IAlugaCarro {

    private final AlugaCarroRepository alugaCarroRepository;

    @Override
    public List<AlugaCarro> buscarTodos() {
        return alugaCarroRepository.findAll();
    }

    @Override
    public BigDecimal somarDebito() {
        return alugaCarroRepository.somarDebito();
    }

    @Override
    public AlugaCarro salvar(AlugaCarro aluguel) {
        return alugaCarroRepository.save(aluguel);
    }
}
