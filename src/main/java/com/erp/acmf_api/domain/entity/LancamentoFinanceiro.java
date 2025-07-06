package com.erp.acmf_api.domain.entity;

import com.erp.acmf_api.domain.enuns.TipoLancamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lancamento_financeiro", schema = "acmf")
public class LancamentoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(name = "tipo", nullable = false, length = 20)
    private TipoLancamento tipo;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;

    @Column(name = "descricao", length = 65535)
    private String descricao;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "conta_destino", length = 100)
    private String contaDestino;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}
