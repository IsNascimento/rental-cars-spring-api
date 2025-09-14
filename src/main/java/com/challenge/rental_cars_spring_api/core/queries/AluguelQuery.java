package com.challenge.rental_cars_spring_api.core.queries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AluguelQuery {
	
	  private final Logger logger = Logger.getLogger(AluguelQuery.class.getName());

	 public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");


    private final AluguelRepository aluguelRepository;
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;

    public List<Aluguel> listarAluguel() {
        return aluguelRepository.findAll();
    }
    
    @Transactional
    public void processarArquivoRentReport() {
        try {
            ClassPathResource resource = new ClassPathResource("RentReport.rtn");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.length() < 20) continue;

                int carroId = Integer.parseInt(linha.substring(0, 2));
                int clienteId = Integer.parseInt(linha.substring(2, 4));
                LocalDate dataAluguel = LocalDate.parse(linha.substring(4, 12), formatter);
                LocalDate dataDevolucao = LocalDate.parse(linha.substring(12, 20), formatter);

                Carro carro = carroRepository.findById((long) carroId).orElse(null);
                Cliente cliente = clienteRepository.findById((long) clienteId).orElse(null);

                if (carro == null) {
                    logger.warning("Carro com ID " + carroId + " não encontrado.");
                    continue;
                }

                if (cliente == null) {
                    logger.warning("Cliente com ID " + clienteId + " não encontrado.");
                    continue;
                }

                long dias = java.time.temporal.ChronoUnit.DAYS.between(dataAluguel, dataDevolucao);
                BigDecimal valor = carro.getVlrDiaria().multiply(BigDecimal.valueOf(dias));

                Aluguel aluguel = new Aluguel();
                aluguel.setCarro(carro);
                aluguel.setCliente(cliente);
                aluguel.setDataAluguel(dataAluguel);
                aluguel.setDataDevolucao(dataDevolucao);
                aluguel.setValor(valor);

                aluguelRepository.save(aluguel);
            }

            reader.close();
        } catch (Exception e) {
           logger.severe("ERRO AO LISTAR ALUGUEL");
        }
    }
}
