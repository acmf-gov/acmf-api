package com.erp.acmf_api.controller.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProdutoCadastroDto {
    private Long id;
    private String nome;
    private String tipo;
    private Integer emEstoque;
    private Boolean status;
    private String observacoes;
    private BigDecimal precoCusto;
    private BigDecimal precoVendaPacote;
}


