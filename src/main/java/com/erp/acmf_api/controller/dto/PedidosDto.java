package com.erp.acmf_api.controller.dto;

import com.erp.acmf_api.domain.enuns.ModalidadePedido;
import com.erp.acmf_api.domain.enuns.StatusPagamento;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PedidosDto {

    private ModalidadePedido modalidadePedido;
    private String observacao;
    private Integer quantidadePedido;
    private StatusPagamento statusPagamento;
    private BigDecimal valorPedido;
    private LocalDateTime dataPedido;

}
