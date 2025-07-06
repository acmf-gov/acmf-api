package com.erp.acmf_api.controller.dto;

import lombok.Data;

@Data
public class PrecoProntoEntregaDto {

    private Long idProduto;
    private Integer quantidade;
    private Integer precoPorUnidade;

}
