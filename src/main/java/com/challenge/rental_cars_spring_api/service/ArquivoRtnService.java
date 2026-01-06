package com.challenge.rental_cars_spring_api.service;

import com.challenge.rental_cars_spring_api.core.domain.AlugaCarro;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.interfaces.*;
import com.challenge.rental_cars_spring_api.exception.ArquivoException;
import com.challenge.rental_cars_spring_api.exception.SistemaException;
import com.challenge.rental_cars_spring_api.util.Format;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArquivoRtnService {

    private final IAlugaCarro alugaCarro;
    private final ICarro icarro;
    private final ICliente icliente;

    public Map<String, Object> processarArquivo() {

        Set<Long> clientesCarroJaProcessados = new HashSet<>();
        Set<Long> novosClientes = new HashSet<>();
        Set<Long> novosCarros = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ArquivoRtnService.class.getResourceAsStream("/RentReport.rtn"), StandardCharsets.UTF_8))) {

            String linha;

            if (reader == null) {
                throw new ArquivoException("Arquivo não fornecido ou vazio");
            }

            while ((linha = reader.readLine()) != null) {
                Long idCarro = Long.parseLong(linha.substring(0, 2).trim());
                Carro novoCarro = new Carro();
                novoCarro.setId(idCarro);

                Long idCliente = Long.parseLong(linha.substring(2, 4).trim());
                Cliente novoCliente = new Cliente();
                novoCliente.setId(idCliente);

                String dataAluguel = linha.substring(4, 12).trim();
                String dataDevolucao = linha.substring(12, 20).trim();

                Optional<Carro> carroOpt = icarro.buscarPorId(idCarro);
                Optional<Cliente> clienteOpt = icliente.buscarPorId(idCliente);

                boolean clienteNovo = !clientesCarroJaProcessados.contains(idCliente);
                boolean carroNovo = !clientesCarroJaProcessados.contains(idCarro);

                if (carroOpt.isPresent() && clienteOpt.isPresent()) {
                    Carro carro = carroOpt.get();
                    int diasAlugados = Format.calcularDias(dataAluguel, dataDevolucao);
                    BigDecimal vlrDiaria =  carro.getVlrDiaria();

                    BigDecimal valor = vlrDiaria.multiply(BigDecimal.valueOf(diasAlugados));

                    AlugaCarro aluguel = new AlugaCarro();
                    aluguel.setCarro(novoCarro);
                    aluguel.setCliente(novoCliente);
                    aluguel.setDataAluguel(LocalDate.parse(dataAluguel, DateTimeFormatter.ofPattern("yyyyMMdd")));
                    aluguel.setDataDevolucao(LocalDate.parse(dataDevolucao, DateTimeFormatter.ofPattern("yyyyMMdd")));
                    aluguel.setValor(valor);

                    if (clienteNovo) {
                        log.info("Cliente novo encontrado: ID {}", idCliente);
                        novosClientes.add(idCliente); // Adiciona o cliente novo
                    }

                    if (carroNovo) {
                        log.info("Carro novo encontrado: ID {}", idCarro);
                        novosCarros.add(idCarro); // Adiciona o carro novo
                    }

                    clientesCarroJaProcessados.add(idCliente);
                    clientesCarroJaProcessados.add(idCarro);

                    alugaCarro.salvar(aluguel);
                    
                } else {
                    if (!carroOpt.isPresent() || !clienteOpt.isPresent()) {
                        StringBuilder mensagemAlerta = new StringBuilder("Faltando: ");
                        if (!carroOpt.isPresent()) {
                            mensagemAlerta.append("Carro ID: ").append(idCarro).append("; ");
                        }
                        if (!clienteOpt.isPresent()) {
                            mensagemAlerta.append("Cliente ID: ").append(idCliente);
                        }
                        log.warn(mensagemAlerta.toString());
                    }
                }
            }
        } catch (IOException e) {
            throw new SistemaException("Erro ao processar arquivo RentReport.rtn", e.getMessage());
        }
 
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("novosClientes", novosClientes);
        resultado.put("novosCarros", novosCarros);
        return resultado;
    }
}
