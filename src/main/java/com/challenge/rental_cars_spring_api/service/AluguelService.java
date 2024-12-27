package com.challenge.rental_cars_spring_api.service;

import com.challenge.rental_cars_spring_api.domain.dto.AluguelDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface AluguelService {
    List<AluguelDTO> listarAlugueis();
    void processarArquivoAlugueis(MultipartFile arquivo);
    List<AluguelDTO> filtrarAlugueis(String data, String modelo);
}
