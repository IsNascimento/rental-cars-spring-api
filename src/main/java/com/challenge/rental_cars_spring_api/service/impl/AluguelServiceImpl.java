package com.challenge.rental_cars_spring_api.service.impl;

import com.challenge.rental_cars_spring_api.domain.Aluguel;
import com.challenge.rental_cars_spring_api.domain.Carro;
import com.challenge.rental_cars_spring_api.domain.Cliente;
import com.challenge.rental_cars_spring_api.domain.dto.AluguelDTO;
import com.challenge.rental_cars_spring_api.domain.mapper.AluguelMapper;
import com.challenge.rental_cars_spring_api.exception.BusinessException;
import com.challenge.rental_cars_spring_api.exception.ResourceNotFoundException;
import com.challenge.rental_cars_spring_api.repository.AluguelRepository;
import com.challenge.rental_cars_spring_api.service.AluguelService;
import com.challenge.rental_cars_spring_api.service.CarroService;
import com.challenge.rental_cars_spring_api.service.ClienteService;
import com.challenge.rental_cars_spring_api.service.FileProcessingService;
import com.challenge.rental_cars_spring_api.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AluguelServiceImpl implements AluguelService {

    private static final Logger logger = LoggerFactory.getLogger(AluguelServiceImpl.class);

    private final AluguelRepository aluguelRepository;
    private final CarroService carroService;
    private final ClienteService clienteService;
    private final AluguelMapper aluguelMapper;
    private final FileProcessingService fileProcessingService;
    private final MessageUtil messageUtil;

    @Override
    public void processarArquivoAlugueis(MultipartFile arquivo) {
        logger.info("Iniciando o processamento do arquivo de aluguéis: {}", arquivo.getOriginalFilename());

        fileProcessingService.processarArquivo(arquivo, ".rtn", 10 * 1024 * 1024, linha -> {
            try {
                Long carroId = Long.parseLong(linha.substring(0, 2).trim());
                Long clienteId = Long.parseLong(linha.substring(2, 4).trim());
                LocalDate dataAluguel = LocalDate.parse(linha.substring(4, 12).trim(), DateTimeFormatter.ofPattern("yyyyMMdd"));
                LocalDate dataDevolucao = LocalDate.parse(linha.substring(12, 20).trim(), DateTimeFormatter.ofPattern("yyyyMMdd"));

                Carro carro = carroService.obterCarroPorId(carroId);
                Cliente cliente = clienteService.obterClientePorId(clienteId);

                Aluguel aluguel = aluguelMapper.criarAluguel(carro, cliente, dataAluguel, dataDevolucao);
                aluguelRepository.save(aluguel);

            } catch (NumberFormatException e) {
                logger.error("Erro ao processar ID ou valor numérico na linha: {}. Detalhes: {}", linha, e.getMessage());
                throw new BusinessException(messageUtil.getMessage("erro.conversao", "linha"));
            } catch (DateTimeParseException e) {
                logger.error("Erro ao processar data na linha: {}. Detalhes: {}", linha, e.getMessage());
                throw new BusinessException(messageUtil.getMessage("erro.data.invalida", "linha"));
            } catch (ResourceNotFoundException e) {
                logger.warn("Recurso não encontrado ao processar linha: {}. Detalhes: {}", linha, e.getMessage());
            } catch (Exception e) {
                logger.error("Erro inesperado ao processar linha: {}. Detalhes: {}", linha, e.getMessage());
                throw new BusinessException(messageUtil.getMessage("erro.processamento.arquivo"));
            }
        });

        logger.info("Processamento do arquivo {} concluído com sucesso.", arquivo.getOriginalFilename());
    }


    @Override
    public List<AluguelDTO> listarAlugueis() {
        logger.info("Buscando todos os registros de aluguéis.");
        return aluguelRepository.findAll().stream()
                .map(aluguelMapper::toDTO)
                .toList();
    }

    @Override
    public List<AluguelDTO> filtrarAlugueis(String data, String modelo) {
        logger.info("Iniciando filtragem de aluguéis. Parâmetros - Data: {}, Modelo: {}", data, modelo);

        try {
            List<Aluguel> alugueis;

            if (data != null && modelo != null) {
                LocalDate dataAluguel = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                logger.info("Filtrando por data e modelo.");
                alugueis = aluguelRepository.findByDataAluguelAndCarroModelo(dataAluguel, modelo);
            } else if (data != null) {
                LocalDate dataAluguel = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                logger.info("Filtrando apenas por data.");
                alugueis = aluguelRepository.findByDataAluguel(dataAluguel);
            } else if (modelo != null) {
                logger.info("Filtrando apenas por modelo.");
                alugueis = aluguelRepository.findByCarroModelo(modelo);
            } else {
                logger.info("Nenhum filtro aplicado, retornando todos os registros.");
                alugueis = aluguelRepository.findAll();
            }

            return alugueis.stream()
                    .map(aluguelMapper::toDTO)
                    .toList();

        } catch (DateTimeParseException e) {
            logger.error("Erro ao processar a data: {}. Detalhes: {}", data, e.getMessage());
            throw new BusinessException(messageUtil.getMessage("erro.data.invalida", "data"));
        } catch (Exception e) {
            logger.error("Erro inesperado ao filtrar aluguéis. Detalhes: {}", e.getMessage());
            throw new BusinessException(messageUtil.getMessage("erro.interno"));
        }
    }
}
