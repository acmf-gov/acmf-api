package com.erp.acmf_api.domain.service.mapper;

import com.erp.acmf_api.controller.dto.ProdutoCadastroDto;
import com.erp.acmf_api.controller.dto.ProdutoConsultaDto;
import com.erp.acmf_api.domain.entity.Produto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProdutoMapper {

    public static Produto toEntity(ProdutoCadastroDto produto) {
        Produto entity = new Produto();
        entity.setId(produto.getId());
        entity.setNome(produto.getNome());
        entity.setTipo(produto.getTipo());
        entity.setEmEstoque(produto.getEmEstoque());
        entity.setAtivo(produto.getStatus());
        entity.setObservacoes(produto.getObservacoes());
        entity.setPrecoCusto(produto.getPrecoCusto());
        entity.setPrecoVendaPacote(produto.getPrecoVendaPacote());
        entity.setDataCriacao(LocalDateTime.now());

        return entity;
    }

    public static ProdutoConsultaDto toProdutoConsultaDto(Produto produto) {
        ProdutoConsultaDto retorno = new ProdutoConsultaDto();
        retorno.setId(produto.getId());
        retorno.setNome(produto.getNome());
        retorno.setTipo(produto.getTipo());
        retorno.setEmEstoque(produto.getEmEstoque() > 0);
        retorno.setPrecoVendaPacote(produto.getPrecoVendaPacote());

        return retorno;
    }

}
