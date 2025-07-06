package com.erp.acmf_api.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente", schema = "acmf")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 250)
    private String nome;

    @Column(name = "telefone", nullable = false, length = 20, unique = true)
    private String telefone;

    @OneToOne
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @Column(name = "credito", precision = 10, scale = 2)
    private BigDecimal credito;

    @Column(name = "referencia", length = 65535, nullable = false)
    private String referencia;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
}