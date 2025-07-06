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
@Table(name = "produto", schema = "acmf")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 250)
    private String nome;

    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "preco_custo", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoCusto;

    @Column(name = "preco_venda_pacote", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVendaPacote;

    @Column(name = "em_estoque", nullable = false)
    private Integer emEstoque;

    @Column(name = "observacoes", length = 65535)
    private String observacoes;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
}