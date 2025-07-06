package com.erp.acmf_api.domain.repository;


import com.erp.acmf_api.domain.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Optional<Endereco> findByRuaAndBairroAndNumero(String rua, String bairro, String numero);

}