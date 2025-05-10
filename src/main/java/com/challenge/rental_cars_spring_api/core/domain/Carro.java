package com.challenge.rental_cars_spring_api.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "carro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modelo", nullable = false, length = 100)
    private String modelo;

    @Column(name = "ano", nullable = false, length = 4)
    private String ano;

    @Column(name = "qtd_passageiros", nullable = false)
    private Integer qtdPassageiros;

    @Column(name = "km", nullable = false)
    private Integer km;

    @Column(name = "fabricante", nullable = false, length = 100)
    private String fabricante;

    @Column(name = "vlr_diaria", nullable = false, precision = 10, scale = 2)
    private BigDecimal vlrDiaria;
}
