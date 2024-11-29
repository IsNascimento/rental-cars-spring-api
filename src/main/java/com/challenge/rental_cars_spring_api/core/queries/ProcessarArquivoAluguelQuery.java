package com.challenge.rental_cars_spring_api.core.queries;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ResultadoProcessamentoArquivoDTO;
import com.challenge.rental_cars_spring_api.exceptions.ErroAoProcessarArquivoException;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessarArquivoAluguelQuery {

    private final AluguelRepository aluguelRepository;
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public ResultadoProcessamentoArquivoDTO processaArquivo(MultipartFile file) {
        try {
            ResultadoProcessamentoArquivoDTO resultado = new ResultadoProcessamentoArquivoDTO(
                    file.getOriginalFilename()
            );

            log.info("Processando arquivo: {}", file.getOriginalFilename());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    file.getInputStream(),
                    StandardCharsets.UTF_8)
            );
            String line;

            while ((line = reader.readLine()) != null) {
                resultado.setTotalRegistros(resultado.getTotalRegistros() + 1);

                if (line.length() != 20) {
                    log.warn("Linha inválida: {}", line);
                    resultado.setTotalRegistrosComErro(resultado.getTotalRegistrosComErro() + 1);
                    continue;
                }

                Long carroId = Long.parseLong(line.substring(0, 2));
                Long clienteId = Long.parseLong(line.substring(2, 4));
                Date dataInicio = converterData(line.substring(4, 12));
                Date dataFim = converterData(line.substring(12, 20));

                Optional<Carro> carroOptional = carroRepository.findById(carroId);
                if (carroOptional.isEmpty()) {
                    log.warn("Carro não encontrado: {}", carroId);
                    resultado.setTotalRegistrosComErro(resultado.getTotalRegistrosComErro() + 1);
                    continue;
                }

                Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);
                if (clienteOptional.isEmpty()) {
                    log.warn("Cliente não encontrado: {}", clienteId);
                    resultado.setTotalRegistrosComErro(resultado.getTotalRegistrosComErro() + 1);
                    continue;
                }

                BigDecimal valor = calcularValor(
                        carroOptional.get().getVlrDiaria(),
                        dataInicio,
                        dataFim
                );

                Aluguel aluguel = Aluguel.builder()
                        .id(null)
                        .carro(carroOptional.get())
                        .cliente(clienteOptional.get())
                        .dataAluguel(dataInicio)
                        .dataDevolucao(dataFim)
                        .valor(valor)
                        .pago(false)
                        .build();
                resultado.setTotalRegistrosProcessados(resultado.getTotalRegistrosProcessados() + 1);
                aluguelRepository.save(aluguel);
            }
            return resultado;

        } catch (Exception ex) {
            log.error("Erro ao processar arquivo", ex);
            throw new ErroAoProcessarArquivoException(String.format(
                    "Erro ao processar arquivo: '%s'",
                    ex.getMessage()
            ));
        }
    }


    private Date converterData(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.parse(dateStr);
    }

    private BigDecimal calcularValor(BigDecimal vlrDiaria, Date dataInicio, Date dataFim) {
        long difEmMilisegundos = Math.abs(dataFim.getTime() - dataInicio.getTime());
        long difEmDias = difEmMilisegundos / (1000 * 60 * 60 * 24);
        return vlrDiaria.multiply(BigDecimal.valueOf(difEmDias));
    }

}
