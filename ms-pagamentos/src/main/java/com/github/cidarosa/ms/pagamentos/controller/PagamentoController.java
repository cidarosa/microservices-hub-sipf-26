package com.github.cidarosa.ms.pagamentos.controller;

import com.github.cidarosa.ms.pagamentos.dto.PagamentoRequestDTO;
import com.github.cidarosa.ms.pagamentos.dto.PagamentoResponseDTO;
import com.github.cidarosa.ms.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

//    @Autowired
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> getAll() {

        List<PagamentoResponseDTO> pagamentoDTO = pagamentoService.findAllPagamentos();

        return ResponseEntity.ok(pagamentoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> getOne(@PathVariable Long id){

        PagamentoResponseDTO pagamentoDTO = pagamentoService.findPagamentoById(id);

        return ResponseEntity.ok(pagamentoDTO);
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> savePagamento(@RequestBody @Valid PagamentoRequestDTO requestDTO){

        PagamentoResponseDTO  responseDTO = pagamentoService.savePagamento(requestDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> updatePagamento(@PathVariable Long id,
                                                                @RequestBody @Valid PagamentoRequestDTO requestDTO){

        PagamentoResponseDTO responseDTO = pagamentoService.update(id, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id){

        pagamentoService.deletePagamentoById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<PagamentoResponseDTO> confirmarPagamentoDoPedido(@PathVariable @NotNull Long id){

        PagamentoResponseDTO dto = pagamentoService.confirmarPagamentoDoPedido(id);

        return ResponseEntity.ok(dto);
    }

}
