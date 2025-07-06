package com.erp.acmf_api.controller.dto;

import com.erp.acmf_api.domain.enuns.ModalidadePedido;
import lombok.Data;

@Data
public class PedidoCadastroDto {
    private String telefone;
    private String observacao;
    private ModalidadePedido modalidadePedido;
    private Integer quantidadeProduto;
    private Long produtoId;
}
