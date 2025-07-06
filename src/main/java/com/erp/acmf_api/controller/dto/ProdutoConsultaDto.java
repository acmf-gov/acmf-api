package com.erp.acmf_api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoConsultaDto {
    private Long id;
    private String nome;
    private String tipo;
    private Boolean emEstoque;
    private BigDecimal precoVendaPacote;
}

