package com.challenge.rental_cars_spring_api.core.queries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.queries.dtos.AlugueisResponse;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarAlugueisQuery {

    private final AluguelRepository aluguelRepository;
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;

    public AlugueisResponse execute() {
        List<ListarAlugueisQueryResultItem> alugueis = aluguelRepository.findAll().stream()
                .map(ListarAlugueisQueryResultItem::from)
                .toList();
        BigDecimal valorTotalNaoPago = calcularValorTotalNaoPago();

        return new AlugueisResponse(alugueis, valorTotalNaoPago);
    }

    @Transactional
    public void processarArquivo(MultipartFile file) throws Exception {

        if (!file.getOriginalFilename().endsWith(".rtn")) {
            throw new IllegalArgumentException("O arquivo deve ter a extensão .rtn");
        }
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                processarLinha(linha);
            }
        }
    }

    private void processarLinha(String linha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Long carroId = Long.parseLong(linha.substring(0, 2).trim());
        Long clienteId = Long.parseLong(linha.substring(2, 4).trim());
        LocalDate dataAluguel = LocalDate.parse(linha.substring(4, 12).trim(), formatter);
        LocalDate dataDevolucao = LocalDate.parse(linha.substring(12, 20).trim(), formatter);

        Carro carro = carroRepository.findById(carroId).orElse(null);
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        // TODO melhorar logs de alerta
        if (carro == null) {
            System.out.println("Alerta: Carro ID " + carroId + " não encontrado.");
            return;
        }

        if (cliente == null) {
            System.out.println("Alerta: Cliente ID " + clienteId + " não encontrado.");
            return;
        }

        long diasAlugados = dataDevolucao.toEpochDay() - dataAluguel.toEpochDay();
        BigDecimal valor = carro.getVlrDiaria().multiply(BigDecimal.valueOf(diasAlugados));

        Aluguel aluguel = new Aluguel();
        aluguel.setCarro(carro);
        aluguel.setCliente(cliente);
        aluguel.setDataAluguel(dataAluguel);
        aluguel.setDataDevolucao(dataDevolucao);
        aluguel.setValor(valor);
        aluguel.setPago(false);

        aluguelRepository.save(aluguel);
    }

    public BigDecimal calcularValorTotalNaoPago() {
        return aluguelRepository.findAll().stream()
                .filter(aluguel -> !aluguel.getPago())
                .map(Aluguel::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
