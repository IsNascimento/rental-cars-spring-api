package com.challenge.rental_cars_spring_api.core.service;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadAluguelService {

    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final AluguelRepository aluguelRepository;
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;

    public void execute(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.length() != 20) {
                    log.warn("Linha inválida: {}. Deve ter pelo 20 caracteres.", line);
                    continue;
                }

                Aluguel aluguel = salvarAluguel(line);

                if (aluguel != null) {
                    log.info("Aluguel salvo com sucesso: {}", aluguel);
                }
            }
        }
    }

    @Transactional
    private Aluguel salvarAluguel(String linha) {
        Aluguel aluguel = new Aluguel();

        carroRepository.findById(Long.valueOf(linha.substring(0, 2)))
                .ifPresentOrElse(aluguel::setCarro, () -> {
                    log.warn("Carro com ID {} não encontrado, aluguel não será salvo.", linha.substring(0, 2));
                });
        clienteRepository.findById(Long.valueOf(linha.substring(2, 4)))
                .ifPresentOrElse(aluguel::setCliente, () -> {
                    log.warn("Cliente com ID {} não encontrado, aluguel não será salvo.", linha.substring(2, 4));
                });

        if (aluguel.getCarro() == null || aluguel.getCliente() == null) {
            return null;
        }

        aluguel.setDataAluguel(LocalDate.parse(linha.substring(4, 12), formatter));
        aluguel.setDataDevolucao(LocalDate.parse(linha.substring(12, 20), formatter));

        if (aluguel.getDataDevolucao().isBefore(aluguel.getDataAluguel())) {
            log.warn("Data de devolução {} é anterior à data de aluguel {}. Aluguel não será salvo.",
                    aluguel.getDataDevolucao(), aluguel.getDataAluguel());
            return null;
        }

        BigDecimal vlrDiaria = aluguel.getCarro().getVlrDiaria();
        long diasLocacao = ChronoUnit.DAYS.between(aluguel.getDataAluguel(), aluguel.getDataDevolucao());

        aluguel.setValor(vlrDiaria.multiply(BigDecimal.valueOf(diasLocacao)));
        aluguel.setPago(false);

        return aluguelRepository.save(aluguel);
    }
}
