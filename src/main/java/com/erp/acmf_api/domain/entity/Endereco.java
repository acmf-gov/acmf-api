package com.erp.acmf_api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "endereco", schema = "acmf")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rua", nullable = false, length = 250)
    private String rua;

    @Column(name = "bairro", nullable = false, length = 150)
    private String bairro;

    @Column(name = "numero", nullable = false, length = 10)
    private String numero;

    @Column(name = "complemento", length = 65535)
    private String complemento;

    @Column(name = "tipo_residencia", length = 250)
    private Integer tipoResidencia;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

}