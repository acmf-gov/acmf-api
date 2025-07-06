package com.erp.acmf_api.controller.dto;

import lombok.Data;

@Data
public class PrecoProntoEntregaConsultaDto {

    private Long id;
    private String nomeProduto;
    private Integer quantidade;
    private Integer precoPorUnidade;

}
