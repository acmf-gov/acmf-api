package com.erp.acmf_api.domain.service.mapper;


import com.erp.acmf_api.controller.dto.ClienteConsultaDto;
import com.erp.acmf_api.domain.entity.Cliente;
import com.erp.acmf_api.domain.entity.Endereco;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public static ClienteConsultaDto toResponse(Cliente cliente) {
        ClienteConsultaDto response = new ClienteConsultaDto();
        response.setId(cliente.getId());
        response.setNome(cliente.getNome());
        response.setTelefone(cliente.getTelefone());
        response.setEndereco(cliente.getEndereco().getRua() + ", " + cliente.getEndereco().getBairro());
        return response;
    }
    public static String getEnderecoCompleto(Cliente cliente) {
        if (cliente == null || cliente.getEndereco() == null) {
            return "Endereço não disponível";
        }

        Endereco e = cliente.getEndereco();

        return e.getRua() + ", " +
                e.getNumero() + ", " +
                e.getBairro() + ", " +
                (e.getComplemento() != null ? e.getComplemento() + ", " : "") +
                "Tipo de residência: " + e.getTipoResidencia();
    }

}