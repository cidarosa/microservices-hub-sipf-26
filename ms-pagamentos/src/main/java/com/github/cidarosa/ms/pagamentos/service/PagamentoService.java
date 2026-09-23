package com.github.cidarosa.ms.pagamentos.service;

import com.github.cidarosa.ms.pagamentos.client.PedidoClient;
import com.github.cidarosa.ms.pagamentos.dto.PagamentoRequestDTO;
import com.github.cidarosa.ms.pagamentos.dto.PagamentoResponseDTO;
import com.github.cidarosa.ms.pagamentos.entities.Pagamento;
import com.github.cidarosa.ms.pagamentos.entities.Status;
import com.github.cidarosa.ms.pagamentos.exceptions.PagamentoAprovadoException;
import com.github.cidarosa.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.pagamentos.repository.PagamentoRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoClient pedidoClient;

    @Transactional(readOnly = true)
    public List<PagamentoResponseDTO> findAllPagamentos() {

        List<Pagamento> pagamentos = pagamentoRepository.findAll();

        return pagamentos.stream()
                .map(PagamentoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public PagamentoResponseDTO findPagamentoById(Long id) {

        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new PagamentoResponseDTO(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO savePagamento(PagamentoRequestDTO requestDTO) {

        Pagamento pagamento = new Pagamento();
        mapDtoToPagamento(requestDTO, pagamento);

        pagamento.setStatus(Status.CRIADO);
        pagamento = pagamentoRepository.save(pagamento);
        return new PagamentoResponseDTO(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO update(Long id, PagamentoRequestDTO requestDTO) {

        try {
            Pagamento pagamento = pagamentoRepository.getReferenceById(id);

            if (pagamento.getStatus().equals(Status.APROVADO)){
                throw new PagamentoAprovadoException(
                        String.format("Pagamento id %d já está aprovado e não pode ser alterado.", id)
                );
            }

            mapDtoToPagamento(requestDTO, pagamento);
            pagamento.setStatus(Status.CRIADO);
            pagamento = pagamentoRepository.save(pagamento);
            return new PagamentoResponseDTO(pagamento);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    @Transactional

    public void deletePagamentoById(Long id) {

        if (!pagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        pagamentoRepository.deleteById(id);
    }

    @Transactional
    public PagamentoResponseDTO confirmarPagamentoDoPedido(Long id) {

        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado. ID: " + id)
                );

        pagamento.setStatus(Status.APROVADO);
        pagamentoRepository.save(pagamento);
        try {
            pedidoClient.confirmarPagamento(pagamento.getPedidoId());
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Pedido não encontrado. ID: " + id);
        } catch (FeignException e){
            throw new RuntimeException("Falha ao se comunicar com ms-pedidos", e);
        }
        return new PagamentoResponseDTO(pagamento);
    }

    private void mapDtoToPagamento(PagamentoRequestDTO requestDTO, Pagamento pagamento) {

        pagamento.setValor(requestDTO.getValor());
        pagamento.setNome(requestDTO.getNome());
        pagamento.setNumeroCartao(requestDTO.getNumeroCartao());
        pagamento.setValidade(requestDTO.getValidade());
        pagamento.setCodigoSeguranca(requestDTO.getCodigoSeguranca());
        pagamento.setPedidoId(requestDTO.getPedidoId());
    }
}
