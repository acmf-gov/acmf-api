package com.erp.acmf_api.controller.dto;

import com.erp.acmf_api.domain.enuns.TipoResidencia;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CadastroClienteRequestDto {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Rua é obrigatória")
    private String rua;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    @NotBlank(message = "Referência é obrigatória")
    private String referencia;

    @NotBlank(message = "Tipo de residencia é obrigatória")
    private TipoResidencia tipoResidencia;

    private String complemento;
    private BigDecimal credito;
}