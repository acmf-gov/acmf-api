package com.erp.acmf_api.controller.dto;

import lombok.Data;

@Data
public class ClienteConsultaDto {
    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
}