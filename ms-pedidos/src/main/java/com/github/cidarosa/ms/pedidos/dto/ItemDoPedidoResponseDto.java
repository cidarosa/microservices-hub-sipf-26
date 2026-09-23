package com.github.cidarosa.ms.pedidos.dto;

import com.github.cidarosa.ms.pedidos.entities.ItemDoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemDoPedidoResponseDto {

    private Long id;
    private Integer quantidade;
    private String descricao;
    private BigDecimal precoUnitario;

    public ItemDoPedidoResponseDto(ItemDoPedido itemDoPedido) {
        id = itemDoPedido.getId();
        quantidade = itemDoPedido.getQuantidade();
        descricao = itemDoPedido.getDescricao();
        precoUnitario = itemDoPedido.getPrecoUnitario();
    }
}
