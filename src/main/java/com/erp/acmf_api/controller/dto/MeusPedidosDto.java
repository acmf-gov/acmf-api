package com.erp.acmf_api.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MeusPedidosDto {

    private List<PedidosDto> listaTodosPedidos;
    private BigDecimal valorTotal;

}
