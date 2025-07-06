package com.erp.acmf_api.domain.repository;

import com.erp.acmf_api.domain.entity.PrecoProntoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PrecoProntoEntregaRepository extends JpaRepository<PrecoProntoEntrega, Long> {

    List<PrecoProntoEntrega> findByProduto_Id(Long produtoId);

    PrecoProntoEntrega findByProdutoIdAndQuantidade(Long produtoId, Integer quantidade);

}
