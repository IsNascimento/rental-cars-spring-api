package com.challenge.rental_cars_spring_api.core.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.queries.ListarAlugueisQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarAlugueisQueryResultItem;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ResultadoImportacaoAluguelDTO;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ResultadoListagemAluguelDTO;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;
    private final ListarAlugueisQuery listarAlugueisQuery;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public ResultadoImportacaoAluguelDTO processarArquivo(MultipartFile file) {
        try {
            log.info("Processando arquivo: {}", file.getOriginalFilename());

            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            String linha;
            int totalRegistros = 0;
            int totalRegistrosProcessados = 0;
            int totalRegistrosComErro = 0;
            List<String> erros = new ArrayList<>();

            while ((linha = reader.readLine()) != null) {
                totalRegistros++;
                if (linha.length() != 20) {
                    logError(erros, "Linha inválida: " + linha, linha);
                    totalRegistrosComErro++;
                    continue;
                }

                Long carroId = parseLong(linha.substring(0, 2), "Carro");
                Long clienteId = parseLong(linha.substring(2, 4), "Cliente");

                if (carroId == null || clienteId == null) {
                    totalRegistrosComErro++;
                    continue;
                }

                Date dataAluguel = parseDate(linha.substring(4, 12), "Data de aluguel");
                Date dataDevolucao = parseDate(linha.substring(12, 20), "Data de devolução");

                if (dataAluguel == null || dataDevolucao == null) {
                    totalRegistrosComErro++;
                    continue;
                }

                Optional<Carro> carroOptional = carroRepository.findById(carroId);
                Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);

                if (carroOptional.isEmpty() || clienteOptional.isEmpty()) {
                    logError(erros, "Carro ou Cliente não encontrado.", linha);
                    totalRegistrosComErro++;
                    continue;
                }

                BigDecimal valor = calcularValor(carroOptional.get().getVlrDiaria(), dataAluguel, dataDevolucao);
                Aluguel aluguel = new Aluguel(null, carroOptional.get(), clienteOptional.get(), dataAluguel, dataDevolucao, valor, false);
                totalRegistrosProcessados++;
                aluguelRepository.save(aluguel);
            }

            return new ResultadoImportacaoAluguelDTO(file.getOriginalFilename(), totalRegistros, totalRegistrosProcessados, totalRegistrosComErro, erros);

        } catch (Exception ex) {
            log.error("Erro ao processar arquivo", ex);
            return null;
        }
    }

    private void logError(List<String> erros, String message, String linha) {
        log.error(message + ": {}", linha);
        erros.add(message + ": " + linha);
    }

    private Long parseLong(String value, String field) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            log.error("{} com id inválido: {}", field, value);
            return null;
        }
    }

    private Date parseDate(String dateStr, String field) {
        try {
            return DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            log.error("{} inválida: {}", field, dateStr);
            return null;
        }
    }

    private BigDecimal calcularValor(BigDecimal valorDiaria, Date dataAluguel, Date dataDevolucao) {
        long diferencaEmMilisegundos = Math.abs(dataDevolucao.getTime() - dataAluguel.getTime());
        long qtdDiasAluguel = diferencaEmMilisegundos / (1000 * 60 * 60 * 24);
        return valorDiaria.multiply(BigDecimal.valueOf(qtdDiasAluguel));
    }

    public ResultadoListagemAluguelDTO consultarAluguel() {
        List<ListarAlugueisQueryResultItem> lista = listarAlugueisQuery.execute();
        BigDecimal valorTotalNaoPago = lista.stream()
                .filter(item -> item.pago().equals("NAO"))
                .map(item -> new BigDecimal(item.valor()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ResultadoListagemAluguelDTO(valorTotalNaoPago, lista);
    }
}
