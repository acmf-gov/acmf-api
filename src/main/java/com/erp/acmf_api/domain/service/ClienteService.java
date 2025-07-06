package com.erp.acmf_api.domain.service;

import com.erp.acmf_api.controller.dto.*;
import com.erp.acmf_api.domain.entity.*;
import com.erp.acmf_api.domain.enuns.ModalidadePedido;
import com.erp.acmf_api.domain.enuns.StatusPagamento;
import com.erp.acmf_api.domain.enuns.TipoLancamento;
import com.erp.acmf_api.domain.exception.BusinessException;
import com.erp.acmf_api.domain.repository.*;
import com.erp.acmf_api.domain.service.mapper.ClienteMapper;
import com.erp.acmf_api.domain.service.mapper.ProdutoMapper;
import com.erp.acmf_api.domain.util.TelefoneUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PrecoProntoEntregaRepository precoProntoEntregaRepository;

    @Autowired
    private PacoteRepository pacoteRepository;

    private static final BigDecimal VALOR_ENTREGA = BigDecimal.valueOf(15);


    @Transactional
    public ClienteResponseDto cadastrarCliente(CadastroClienteRequestDto request) {

        if (clienteRepository.findByTelefone(TelefoneUtil.desformatarTelefone(request.getTelefone())) != null) {
            throw new BusinessException("Cliente com esse telefone já cadastrado.");
        }

        Endereco endereco = enderecoRepository
                .findByRuaAndBairroAndNumero(request.getRua(), request.getBairro(), request.getNumero())
                .orElseGet(() -> {
                    Endereco novoEndereco = new Endereco();
                    novoEndereco.setRua(request.getRua());
                    novoEndereco.setBairro(request.getBairro());
                    novoEndereco.setNumero(request.getNumero());
                    novoEndereco.setComplemento(request.getComplemento());
                    novoEndereco.setTipoResidencia(request.getTipoResidencia().getCode());
                    novoEndereco.setDataCriacao(LocalDateTime.now());
                    return enderecoRepository.save(novoEndereco);
                });

        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setTelefone(TelefoneUtil.desformatarTelefone(request.getTelefone()));
        cliente.setEndereco(endereco);
        cliente.setDataCriacao(LocalDateTime.now());
        cliente.setCredito(request.getCredito() != null ? request.getCredito() : BigDecimal.ZERO);
        cliente.setReferencia(request.getReferencia());

        Cliente salvo = clienteRepository.save(cliente);

        return ClienteMapper.toResponse(salvo);
    }

    public List<ProdutoConsultaDto> consultaProdutosDisponiveis() throws BusinessException{
        try {

            List<Produto> listaDeProdutos = produtoRepository.findProdutosDiponiveis();
            List<ProdutoConsultaDto> retorno = new ArrayList<>();
            listaDeProdutos.forEach(p -> retorno.add(ProdutoMapper.toProdutoConsultaDto(p)));
            return retorno;

        }catch (BusinessException e){
            log.info("Ocorreu um erro no consultaProdutosDisponiveis(): {}", e.getMessage());
            throw new BusinessException("Ocorreu um erro.");
        }
    }

    public List<ClienteResponseDto> consultarTodosClientes(){
        try {

            List<ClienteResponseDto> listaRetorno = new ArrayList<>();

            log.info("Listando todos os clientes cadastrados no sistema.");
            List<Cliente> todosOsClientes = clienteRepository.findAll();
            log.info("{} clientes encontrados", todosOsClientes.size());

            for (Cliente cliente : todosOsClientes) {
                ClienteResponseDto clienteDto = new ClienteResponseDto();
                clienteDto.setId(cliente.getId());
                clienteDto.setNome(cliente.getNome());
                clienteDto.setEndereco(ClienteMapper.getEnderecoCompleto(cliente));
                clienteDto.setTelefone(cliente.getTelefone());
                listaRetorno.add(clienteDto);
            }

            return listaRetorno;

        }catch (BusinessException e){
            log.info("Ocorreu um erro no consultarTodosClientes(): {}", e.getMessage());
            throw new BusinessException("Ocorreu um erro.");
        }
    }

    public MeusPedidosDto consultarMeusPedidos(String numeroTelefone){
        try {

            MeusPedidosDto retorno = new MeusPedidosDto();
            List<PedidosDto> listaPedidoDto = new ArrayList<>();
            BigDecimal valorTotalPedido = new BigDecimal(0);

            log.info("Listando todos os pedidos do cliente {} cadastrado no sistema.", numeroTelefone);
            List<Pedido> todosOsPedidos = pedidoRepository.findAllByTelefone(numeroTelefone);
            log.info("{} pedidos encontrados", todosOsPedidos.size());

            for (Pedido pedido : todosOsPedidos) {
                PedidosDto pedidosDto = new PedidosDto();
                pedidosDto.setValorPedido(pedido.getValorTotal());
                pedidosDto.setModalidadePedido(pedido.getModalidadePedido());
                pedidosDto.setDataPedido(pedido.getDataPedido());
                pedidosDto.setQuantidadePedido(pedido.getQuantidadePedido());
                pedidosDto.setObservacao(pedido.getObservacao());
                pedidosDto.setStatusPagamento(pedido.getStatusPagamento());
                listaPedidoDto.add(pedidosDto);

                valorTotalPedido = valorTotalPedido.add(pedido.getValorTotal());
            }

            retorno.setListaTodosPedidos(listaPedidoDto);
            retorno.setValorTotal(valorTotalPedido);
            return retorno;

        }catch (BusinessException e){
            log.info("Ocorreu um erro no consultarTodosClientes(): {}", e.getMessage());
            throw new BusinessException("Ocorreu um erro.");
        }
    }

    public Optional<Cliente> consultarClientePorId(Long idCliente){
        try {

            return clienteRepository.findById(idCliente);

        }catch (BusinessException e){
            log.info("Ocorreu um erro no consultarTodosClientes(): {}", e.getMessage());
            throw new BusinessException("Ocorreu um erro.");
        }
    }

    public boolean realizarPedido(PedidoCadastroDto dto) throws BusinessException {

        try {

            if (!clienteCadastrado(dto.getTelefone())) {
                log.info("Telefone não cadastrado para nenhum cliente: ", dto.getTelefone());
                throw new BusinessException("Telefone não cadastrado");
            }

            if (!produtoDisponivel(dto.getProdutoId())) {
                log.info("Tentativa de pedido para produto indisponível: produtoId={}", dto.getProdutoId());
                throw new BusinessException("Produto não está mais disponível.");
            }

            if (dto.getModalidadePedido().equals(ModalidadePedido.PRONTO_ENTREGA)) {

                List<PrecoProntoEntrega> listaDePrecos = precoProntoEntregaRepository.findByProduto_Id(dto.getProdutoId());

                //Ordenando do maior para o menor
                listaDePrecos.sort((p1, p2) -> Integer.compare(p2.getQuantidade(), p1.getQuantidade()));

                PrecoProntoEntrega precoSelecionado = null;
                for (PrecoProntoEntrega preco : listaDePrecos) {
                    if (dto.getQuantidadeProduto() >= preco.getQuantidade()) {
                        precoSelecionado = preco;
                        break;
                    }
                }

                if (precoSelecionado == null) {
                    throw new BusinessException("Não há preço cadastrado para a quantidade informada.");
                }

                BigDecimal precoUnitario = BigDecimal.valueOf(precoSelecionado.getPrecoPorUnidade());
                BigDecimal quantidade = BigDecimal.valueOf(dto.getQuantidadeProduto());
                BigDecimal precoTotal = precoUnitario.multiply(quantidade).add(VALOR_ENTREGA);
                log.info("Preço final do pedido: R$ {}", precoTotal);

                gravaPedido(ModalidadePedido.PRONTO_ENTREGA, precoTotal, dto);

                return true;

            }

            if (dto.getModalidadePedido().equals(ModalidadePedido.EM_PACOTE)) {

                Produto produto = produtoRepository.findByID(dto.getProdutoId());

                BigDecimal precoUnitario = produto.getPrecoVendaPacote();
                BigDecimal quantidade = BigDecimal.valueOf(dto.getQuantidadeProduto());
                BigDecimal precoTotal = precoUnitario.multiply(quantidade).add(VALOR_ENTREGA);

                log.info("Preço final do pedido: R$ {}", precoTotal);

                gravaPacote(ModalidadePedido.EM_PACOTE, precoTotal, dto);
                return true;

            }

            return false;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao realizar pedido: {}", e.getMessage(), e);
            throw new BusinessException("Erro ao realizar pedido.");
        }
    }

    public void cancelarPedido(Long idPedido, String numeroSolicitante) throws BusinessException {
        try {

            Pedido pedido = pedidoRepository.findByIdPedido(idPedido);

            validaCancelamentoDePedido(pedido, numeroSolicitante);

            if (pedido.getStatusPagamento().equals(StatusPagamento.PAGO) ||
            pedido.getStatusPagamento().equals(StatusPagamento.PAGO_SEM_ENTREGA)){
                //lancamento financeiro aq
            }

            pedido.setStatusPagamento(StatusPagamento.CANCELADO);

            pedidoRepository.save(pedido);

        } catch (BusinessException e) {
            log.error("Erro ao cancelar pedido: {}", e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    private void validaCancelamentoDePedido(Pedido pedido, String numeroSolicitante){
        try {

            if (pedido == null ){
                throw new BusinessException("Pedido não encontrado");
            }

            if (pedido.getStatusPagamento().equals(StatusPagamento.CANCELADO)){
                throw new BusinessException("Pedido já cancelado");
            }

            if (!(pedido.getCliente().getTelefone().equals(numeroSolicitante))){
                throw new BusinessException("Pedido não pertence ao solicitante.");
            }

        }catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private void gravaPedido(ModalidadePedido modalidadePedido, BigDecimal precoTotal, PedidoCadastroDto dto){
        try{

            log.info("Gravando informações do pedido={}", dto.toString());
            Cliente cliente = clienteRepository.findByTelefone(TelefoneUtil.desformatarTelefone(dto.getTelefone()));

            Pedido pedidoFinal = new Pedido();
            pedidoFinal.setCliente(cliente);
            pedidoFinal.setDataPedido(LocalDateTime.now());
            pedidoFinal.setModalidadePedido(modalidadePedido);
            pedidoFinal.setQuantidadePedido(dto.getQuantidadeProduto());
            pedidoFinal.setObservacao(dto.getObservacao());
            pedidoFinal.setValorTotal(precoTotal);
            pedidoFinal.setStatusPagamento(StatusPagamento.PENDENTE);

            pedidoRepository.save(pedidoFinal);

        } catch (Exception e) {
            log.error("Erro ao realizar o pedido: {}", e.getMessage(), e);
            throw new BusinessException("Houve um erro ao tentar realizar o pedido.");
        }
    }

    private void gravaPacote(ModalidadePedido modalidadePedido, BigDecimal precoTotal, PedidoCadastroDto dto){
        try{

            log.info("Gravando informações do pedido={}", dto.toString());

            Cliente cliente = clienteRepository.findByTelefone(TelefoneUtil.desformatarTelefone(dto.getTelefone()));
            Produto produto = produtoRepository.findByID(dto.getProdutoId());

            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setDataPedido(LocalDateTime.now());
            pedido.setModalidadePedido(modalidadePedido);
            pedido.setQuantidadePedido(dto.getQuantidadeProduto());
            pedido.setObservacao(dto.getObservacao());
            pedido.setValorTotal(precoTotal);
            pedido.setStatusPagamento(StatusPagamento.PENDENTE);

            Pedido pedidoFinal = pedidoRepository.save(pedido);

            Pacotes pacotes = new Pacotes();
            pacotes.setCliente(cliente);
            pacotes.setProduto(produto);
            pacotes.setPedido(pedidoFinal);
            pacotes.setQuantidade(dto.getQuantidadeProduto());
            pacotes.setStatus(StatusPagamento.PENDENTE.getCode());
            pacotes.setDataInicio(produto.getDataCriacao());
            pacotes.setDataFimPrevisto(produto.getDataCriacao().plusDays(7));

            pacoteRepository.save(pacotes);

        } catch (Exception e) {
            log.error("Erro ao realizar o pedido: {}", e.getMessage(), e);
            throw new BusinessException("Houve um erro ao tentar realizar o pedido.");
        }
    }

    private boolean produtoDisponivel(Long idProduto){
        return produtoRepository.countByIdAndAtivoTrue(idProduto) > 0;
    }

    private boolean clienteCadastrado(String numeroTelefone) {
        return clienteRepository.countByTelefone(TelefoneUtil.desformatarTelefone(numeroTelefone)) > 0;
    }

}
