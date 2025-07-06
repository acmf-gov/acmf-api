package com.erp.acmf_api.domain.service.mapper;

import com.erp.acmf_api.controller.dto.PacotesConsultaDto;
import com.erp.acmf_api.domain.entity.Pacotes;
import com.erp.acmf_api.domain.enuns.StatusPagamento;

public class PacotesMapper {
    public static PacotesConsultaDto toResponse(Pacotes pacotes) {
        PacotesConsultaDto response = new PacotesConsultaDto();
        response.setStatus(StatusPagamento.fromCodigo(pacotes.getStatus()));
        response.setQuantidade(pacotes.getQuantidade());
        response.setNomeCliente(pacotes.getCliente().getNome());
        response.setTelefoneCliente(pacotes.getCliente().getTelefone());
        response.setNomeProduto(pacotes.getProduto().getNome());
        response.setDataFimPrevisto(pacotes.getDataFimPrevisto());
        response.setEnderecoEntrega(ClienteMapper.getEnderecoCompleto(pacotes.getCliente()));
        response.setValorTotal(pacotes.getPedido().getValorTotal());
        return response;
    }
}
