package com.challenge.rental_cars_spring_api.service.impl;

import com.challenge.rental_cars_spring_api.exception.FileProcessingException;
import com.challenge.rental_cars_spring_api.service.FileProcessingService;
import com.challenge.rental_cars_spring_api.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileProcessingServiceImpl implements FileProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(FileProcessingServiceImpl.class);
    private final MessageUtil messageUtil;

    @Override
    public void processarArquivo(MultipartFile arquivo, String extensaoEsperada, long tamanhoMaximo, LinhaProcessor linhaProcessor) {
        validarArquivo(arquivo, extensaoEsperada, tamanhoMaximo);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(arquivo.getInputStream()))) {
            logger.info("Iniciando o processamento do arquivo: {}", arquivo.getOriginalFilename());

            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    linhaProcessor.processar(linha);
                } catch (Exception e) {
                    logger.warn("Erro ao processar linha: {}. Detalhes: {}", linha, e.getMessage());
                }
            }

            logger.info("Processamento do arquivo {} concluído com sucesso.", arquivo.getOriginalFilename());
        } catch (Exception e) {
            logger.error("Erro ao processar o arquivo: {}", arquivo.getOriginalFilename(), e);
            throw new FileProcessingException(messageUtil.getMessage("erro.processamento.arquivo"), e);
        }
    }

    private void validarArquivo(MultipartFile arquivo, String extensaoEsperada, long tamanhoMaximo) {
        if (arquivo.isEmpty() || !Objects.requireNonNull(arquivo.getOriginalFilename()).endsWith(extensaoEsperada)) {
            throw new FileProcessingException(messageUtil.getMessage("arquivo.extensao.invalida", extensaoEsperada));
        }
        if (arquivo.getSize() > tamanhoMaximo) {
            throw new FileProcessingException(messageUtil.getMessage("arquivo.tamanho.excedido", tamanhoMaximo / (1024 * 1024)));
        }
    }
}
