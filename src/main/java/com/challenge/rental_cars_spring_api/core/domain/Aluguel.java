package com.challenge.rental_cars_spring_api.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "aluguel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Carro carro;
    @ManyToOne
    private Cliente cliente;
    private LocalDate dataAluguel;
    private LocalDate dataDevolucao;
    private BigDecimal valor;
    private boolean pago;
}
