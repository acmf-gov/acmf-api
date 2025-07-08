package com.erp.acmf_api.domain.service;

import com.erp.acmf_api.controller.dto.*;
import com.erp.acmf_api.domain.entity.Cliente;
import com.erp.acmf_api.domain.entity.Pacotes;
import com.erp.acmf_api.domain.entity.PrecoProntoEntrega;
import com.erp.acmf_api.domain.entity.Produto;
import com.erp.acmf_api.domain.exception.BusinessException;
import com.erp.acmf_api.domain.repository.ClienteRepository;
import com.erp.acmf_api.domain.repository.PacotesRepository;
import com.erp.acmf_api.domain.repository.PrecoProntoEntregaRepository;
import com.erp.acmf_api.domain.repository.ProdutoRepository;
import com.erp.acmf_api.domain.service.mapper.PacotesMapper;
import com.erp.acmf_api.domain.service.mapper.ProdutoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministradorService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PrecoProntoEntregaRepository precoProntoEntregaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PacotesRepository pacotesRepository;

    public void adicionarProduto(ProdutoCadastroDto dto) throws BusinessException {
        try {

            Produto produto = produtoRepository.findByNome(dto.getNome());
            if(produto != null){
                throw new BusinessException("Produto já cadastrado no banco! Favor verifique");
            }

            produtoRepository.save(ProdutoMapper.toEntity(dto));

        } catch (Exception e) {
            log.error("Erro ao adicionar produto: {}", e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public void removerProduto(Long idProduto) throws BusinessException {
        try {

            if (produtoRepository.findById(idProduto).isEmpty()){
                throw new BusinessException("Produto não existe");
            }

            produtoRepository.deleteById(idProduto);

        } catch (Exception e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            throw new BusinessException("Erro ao remover produto.");
        }
    }

    public void removerPrecoProntoEntrega(Long idPreco) throws BusinessException {
        try {

            if (precoProntoEntregaRepository.findById(idPreco).isEmpty()){
                throw new BusinessException("Preco para produto pronto entrega não existe");
            }

            precoProntoEntregaRepository.deleteById(idPreco);

        } catch (Exception e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            throw new BusinessException("Erro ao remover produto.");
        }
    }

    public void atualizarProduto(Long idProduto, ProdutoCadastroDto dto) throws BusinessException {
        try {

            if (produtoRepository.findById(idProduto).isEmpty()){
                throw new BusinessException("Produto não existe");
            }

            dto.setId(idProduto);
            produtoRepository.save(ProdutoMapper.toEntity(dto));

        } catch (Exception e) {
            log.error("Erro ao atualizar produto: {}", e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public void adicionarPrecoProntoEntrega(PrecoProntoEntregaDto dto) throws BusinessException {
        try {

            if(!produtoExiste(dto.getIdProduto())){
                throw new BusinessException("Produto não cadastrado");
            }

            if(precoProntoEntregaExiste(dto.getIdProduto(), dto.getQuantidade())){
                throw new BusinessException("Preco por quantidade já cadastrado no banco! Favor verifique");
            }

            Produto produto = produtoRepository.findByID(dto.getIdProduto());
            PrecoProntoEntrega precoFinal = new PrecoProntoEntrega();
            precoFinal.setPrecoPorUnidade(dto.getPrecoPorUnidade());
            precoFinal.setQuantidade(dto.getQuantidade());
            precoFinal.setProduto(produto);

            precoProntoEntregaRepository.save(precoFinal);

        } catch (Exception e) {
            log.error("Erro ao adicionar produto: {}", e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public boolean validaClientePorNumero(String numeroTelefone){
        try {

            log.info("Consultando se {} é um cliente cadastrado", numeroTelefone);
            Cliente cliente = clienteRepository.findByTelefone(numeroTelefone);
            if(cliente != null){
                log.info("Cliente encontrado!");
                return true;
            }
            return false;

        }catch (Exception e) {
            log.error("Erro ao validar se cliente é cadastrado ou não pelo numero de telefone recebido: {}", e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public List<PrecoProntoEntregaConsultaDto> consultarPrecoProntoEntrega(Long idProduto){

        try {

            if(!produtoExiste(idProduto)){
                throw new BusinessException("Produto não existe");
            }

            List<PrecoProntoEntrega> preco = precoProntoEntregaRepository.findByProduto_Id(idProduto);
            return getPrecoProntoEntregaConsultaDtos(preco);

        }catch (BusinessException e) {
            log.error("Erro ao consultar preço: {}", e.getMessage());
            throw new BusinessException(e.getMessage());
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("Houve um erro inesperado!");
        }

    }

    public List<PacotesConsultaDto> consultarPacote(){

        try {

            List<PacotesConsultaDto> listaRetorno = new ArrayList<>();
            List<Pacotes> pacotes = pacotesRepository.findAll();
            for (Pacotes pacote : pacotes) {
                listaRetorno.add(PacotesMapper.toResponse(pacote));
            }
            return listaRetorno;

        }catch (BusinessException e) {
            log.error("Erro ao consultar preço: {}", e.getMessage());
            throw new BusinessException(e.getMessage());
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("Houve um erro inesperado!");
        }

    }

    private List<PrecoProntoEntregaConsultaDto> getPrecoProntoEntregaConsultaDtos(List<PrecoProntoEntrega> preco) {
        List<PrecoProntoEntregaConsultaDto> retorno = new ArrayList<>();

        for (PrecoProntoEntrega prontoEntrega : preco) {
            PrecoProntoEntregaConsultaDto precoRetorno = new PrecoProntoEntregaConsultaDto();
            precoRetorno.setId(prontoEntrega.getId());
            precoRetorno.setNomeProduto(prontoEntrega.getProduto().getNome());
            precoRetorno.setQuantidade(prontoEntrega.getQuantidade());
            precoRetorno.setPrecoPorUnidade(prontoEntrega.getPrecoPorUnidade());
            retorno.add(precoRetorno);
        }
        return retorno;
    }

    private boolean produtoExiste(Long idProduto){
        return produtoRepository.findByID(idProduto) != null;
    }

    private boolean precoProntoEntregaExiste(Long idProduto, Integer quantidade){
        return precoProntoEntregaRepository.findByProdutoIdAndQuantidade(idProduto, quantidade) != null;
    }

}
