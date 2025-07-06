package com.erp.acmf_api.controller;

import com.erp.acmf_api.controller.dto.*;
import com.erp.acmf_api.domain.exception.BusinessException;
import com.erp.acmf_api.domain.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastro-cliente")
    public ResponseEntity<?> cadastrarCliente(@RequestBody CadastroClienteRequestDto request) {

        try {
            ClienteResponseDto response = clienteService.cadastrarCliente(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (BusinessException e){
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @GetMapping("/consultar-produtos-diponiveis")
    public ResponseEntity<?> consultarProdutosDiponiveis() throws BusinessException{
        try{

            List<ProdutoConsultaDto> produtos = clienteService.consultaProdutosDisponiveis();

            if(produtos.isEmpty()){
                return ResponseEntity.ok("Não há produtos disponíveis no momento.");
            }

            return ResponseEntity.ok(Collections.singletonList(produtos));

        }catch (BusinessException e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/meus-pedidos/{numeroTelefone}")
    public ResponseEntity<?> consultarProdutosDiponiveis(@PathVariable String numeroTelefone) throws BusinessException{
        try{

            MeusPedidosDto meusPedidos = clienteService.consultarMeusPedidos(numeroTelefone);
            return ResponseEntity.ok(meusPedidos);

        }catch (BusinessException e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @PostMapping("/realizar-pedido")
    public ResponseEntity<?> realizarPedido(@RequestBody PedidoCadastroDto dto) {
        try {
            if (clienteService.realizarPedido(dto)){
                return ResponseEntity.status(HttpStatus.CREATED).body("Pedido realizado com sucesso.");
            }
            return ResponseEntity.unprocessableEntity().body("Houve algum erro durante o pedido..");
        } catch (BusinessException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/cancelar-pedido/{idPedido}/{numeroTelefone}")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long idPedido, @PathVariable String numeroTelefone) {
        try {
            clienteService.cancelarPedido(idPedido, numeroTelefone);
            return ResponseEntity.status(HttpStatus.OK).body("Pedido cancelado com sucesso.");
        } catch (BusinessException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

}
