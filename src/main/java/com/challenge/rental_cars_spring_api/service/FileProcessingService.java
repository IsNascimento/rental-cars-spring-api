package com.challenge.rental_cars_spring_api.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileProcessingService {
    void processarArquivo(MultipartFile arquivo, String extensaoEsperada, long tamanhoMaximo, LinhaProcessor linhaProcessor);

    @FunctionalInterface
    interface LinhaProcessor {
        void processar(String linha) throws Exception;
    }
}
