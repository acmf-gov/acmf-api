package com.erp.acmf_api.domain.repository;

import com.erp.acmf_api.domain.entity.Cliente;
import com.erp.acmf_api.domain.entity.Pedido;
import com.erp.acmf_api.domain.enuns.ModalidadePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(value = "SELECT * FROM acmf.pedido p WHERE p.id = :id", nativeQuery = true)
    Pedido findByIdPedido(@Param("id") Long id);

    @Query(value = """
        SELECT p.*
        FROM acmf.pedido p
        INNER JOIN acmf.cliente c ON p.cliente_id = c.id
        WHERE c.telefone LIKE %:telefone%
    """, nativeQuery = true)
    List<Pedido> findAllByTelefone(@Param("telefone") String telefone);


}
