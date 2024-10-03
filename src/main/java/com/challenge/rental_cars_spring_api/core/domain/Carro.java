package com.challenge.rental_cars_spring_api.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "carro")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Carro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "ano", length = 4)
    private String ano;

    @Column(name = "qtd_passageiros")
    private Integer qtdPassageiros;

    @Column(name = "km")
    private Integer km;

    @Column(name = "fabricante", length = 50)
    private String fabricante;

    @Column(name = "vlr_diaria", precision = 7, scale = 2)
    private BigDecimal vlrDiaria;
}
