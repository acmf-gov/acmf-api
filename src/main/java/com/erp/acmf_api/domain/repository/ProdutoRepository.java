package com.erp.acmf_api.domain.repository;

import com.erp.acmf_api.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findByNome(String nome);

    @Query(value = "SELECT * FROM acmf.produto p WHERE p.id = :id", nativeQuery = true)
    Produto findByID(@Param("id") Long id);

    @Query(value = "SELECT * FROM acmf.produto p WHERE p.ativo = true", nativeQuery = true)
    List<Produto> findProdutosDiponiveis();

    Integer countByIdAndAtivoTrue(Long id);

}
