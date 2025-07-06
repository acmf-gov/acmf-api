package com.erp.acmf_api.controller.dto;

import com.erp.acmf_api.domain.enuns.StatusPagamento;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PacotesConsultaDto {

    private String nomeCliente;
    private String telefoneCliente;
    private String nomeProduto;
    private String enderecoEntrega;
    private BigDecimal valorTotal;
    private StatusPagamento status;
    private Integer quantidade;
    private LocalDateTime dataFimPrevisto;

}
