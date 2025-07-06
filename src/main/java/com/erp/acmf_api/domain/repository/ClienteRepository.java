package com.erp.acmf_api.domain.repository;


import com.erp.acmf_api.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByTelefone(String telefone);

    @Query(value = "SELECT COUNT(*) FROM acmf.cliente p WHERE p.telefone like :telefone", nativeQuery = true)
    Integer countByTelefone(String telefone);

}