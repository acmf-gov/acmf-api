package com.erp.acmf_api.controller;

import com.erp.acmf_api.controller.dto.*;
import com.erp.acmf_api.domain.entity.Cliente;
import com.erp.acmf_api.domain.exception.BusinessException;
import com.erp.acmf_api.domain.service.AdministradorService;
import com.erp.acmf_api.domain.service.ClienteService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/adicionar-produto")
    public ResponseEntity<?> adicionarProduto(@RequestBody ProdutoCadastroDto dto) {
        try {
            administradorService.adicionarProduto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Produto salvo com sucesso.");
        } catch (BusinessException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover-produto/{id}")
    public ResponseEntity<?> removerProduto(@Parameter Long idProduto) {
        try {
            administradorService.removerProduto(idProduto);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso.");
        } catch (BusinessException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/remover-preco-pronto-entrega/{idProduto}")
    public ResponseEntity<?> removerPrecoProntoEntrega(@Parameter Long idPreco) {
        try {
            administradorService.removerPrecoProntoEntrega(idPreco);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso.");
        } catch (BusinessException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/atualiza-produto/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoCadastroDto dto) {
        try {
            administradorService.atualizarProduto(id, dto);
            return ResponseEntity.status(HttpStatus.OK).body("Produto atualizado com sucesso.");
        } catch (BusinessException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/adicionar-preco-pronto-entrega")
    public ResponseEntity<?> adicionarPrecoProntoEntrega(@RequestBody PrecoProntoEntregaDto dto) {
        try {
            administradorService.adicionarPrecoProntoEntrega(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Preço salvo com sucesso.");
        } catch (BusinessException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/consultar-preco-pronto-entrega/{idProduto}")
    public ResponseEntity<?> consultarPrecoProntoEntrega(@PathVariable Long idProduto) {
        try {
            List<PrecoProntoEntregaConsultaDto> tabela = administradorService.consultarPrecoProntoEntrega(idProduto);
            if (tabela.isEmpty()){
                return ResponseEntity.ok().body("Não há tabela de preço a pronta entrega para esse produto.");
            }
            return ResponseEntity.ok(Collections.singletonList(tabela));
        } catch (BusinessException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/consultar-pacote")
    public ResponseEntity<?> consultarPacote() {
        try {
            List<PacotesConsultaDto> pacotes = administradorService.consultarPacote();
            if (pacotes.isEmpty()){
                return ResponseEntity.ok().body("Não há nenhum pacote vigente no momento.");
            }
            return ResponseEntity.ok(Collections.singletonList(pacotes));
        } catch (BusinessException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/consultar-todos-clientes")
    public ResponseEntity<?> consultarTodosClientes() {
        try {
            List<ClienteConsultaDto> clientes = clienteService.consultarTodosClientes();
            if (clientes.isEmpty()){
                return ResponseEntity.ok().body("Não há nenhum cliente cadastrado.");
            }
            return ResponseEntity.ok(Collections.singletonList(clientes));
        } catch (BusinessException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/consultar-cliente/{idCliente}")
    public ResponseEntity<?> consultarClientePorId(@PathVariable Long idCliente) {
        try {
            Optional<Cliente> cliente = clienteService.consultarClientePorId(idCliente);
            if (cliente.isEmpty()){
                return ResponseEntity.ok().body("Cliente não encontrado com esse id");
            }
            return ResponseEntity.ok(cliente);
        } catch (BusinessException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/valida-cliente/{numeroTelefone}")
    public ResponseEntity<Boolean> validaClientePorNumero(@PathVariable String numeroTelefone) {
        try {
            return ResponseEntity.ok(administradorService.validaClientePorNumero(numeroTelefone));
        } catch (BusinessException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
