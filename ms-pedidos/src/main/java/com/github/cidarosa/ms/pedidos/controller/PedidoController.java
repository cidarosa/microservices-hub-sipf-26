package com.github.cidarosa.ms.pedidos.controller;

import com.github.cidarosa.ms.pedidos.dto.PedidoResponseDto;
import com.github.cidarosa.ms.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/port")
    public String port(@Value("${local.server.port}") String porta){
        return "Instância respondeu na porta: " + porta;
    }

    @GetMapping
    private ResponseEntity<List<PedidoResponseDto>> getAllPedidos(){

        List<PedidoResponseDto> list = pedidoService.findAllPedidos();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> getPedido(@PathVariable Long id){

        PedidoResponseDto pedidoDto = pedidoService.findPedidoById(id);

        return ResponseEntity.ok(pedidoDto);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDto> createPedido(@RequestBody @Valid PedidoResponseDto pedidoDto){

        pedidoDto = pedidoService.savePedido(pedidoDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pedidoDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(pedidoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> updatePedido(@PathVariable Long id,
                                                          @Valid @RequestBody PedidoResponseDto pedidoDto){

        pedidoDto = pedidoService.updatePedido(id, pedidoDto);

        return ResponseEntity.ok(pedidoDto);
    }

    @PutMapping("/{pedidoId}/pagamento/confirmado")
    public void confirmarPagamento(@PathVariable Long pedidoId){

        pedidoService.confirmarPagamento(pedidoId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id){

        pedidoService.deletePedidoById(id);
        
        return ResponseEntity.noContent().build();
    }

}
