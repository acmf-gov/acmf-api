package com.erp.acmf_api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comissao_colaborador", schema = "acmf")
public class ComissaoColaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false, referencedColumnName = "id", columnDefinition = "BIGINT UNSIGNED")
    private Colaborador colaborador;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "valor_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorBase;

    @Column(name = "percentual", nullable = false, precision = 5, scale = 2)
    private BigDecimal percentual;

    @Column(name = "valor_comissao", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorComissao;

    @Column(name = "pago")
    private Boolean pago;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}