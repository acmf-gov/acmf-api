package com.erp.acmf_api.domain.entity;

import com.erp.acmf_api.domain.enuns.ModalidadePedido;
import com.erp.acmf_api.domain.enuns.StatusPagamento;
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
@Table(name = "pedido", schema = "acmf")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "entregador_id", referencedColumnName = "id", columnDefinition = "BIGINT UNSIGNED")
    private Colaborador entregador;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade_pedido")
    private ModalidadePedido modalidadePedido;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "quantidade_pedido")
    private Integer quantidadePedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento", nullable = false, length = 20)
    private StatusPagamento statusPagamento;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

}