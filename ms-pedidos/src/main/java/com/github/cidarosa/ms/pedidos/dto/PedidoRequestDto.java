package com.github.cidarosa.ms.pedidos.dto;

import com.github.cidarosa.ms.pedidos.entities.ItemDoPedido;
import com.github.cidarosa.ms.pedidos.entities.Pedido;
import com.github.cidarosa.ms.pedidos.entities.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidoRequestDto {


    @NotBlank(message = "Nome é requerido")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    // @CPF(message = "Informe um CPF válido")
    @NotBlank(message = "CPF é requerido")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;

//    private LocalDate data;

//    private Status status;

//    private BigDecimal valorTotal;

    @NotEmpty(message = "Pedido deve ter pelo menos um item")
    private List<@Valid ItemDoPedidoRequestDto> itens = new ArrayList<>();

//    public PedidoRequestDto(Pedido pedido) {
////        id = pedido.getId();
//        nome = pedido.getNome();
//        cpf = pedido.getCpf();
////        data = pedido.getData();
////        status = pedido.getStatus();
////        valorTotal = pedido.getValorTotal();
//
//        for(ItemDoPedido item : pedido.getItens()){
//
//            ItemDoPedidoRequestDto itemDTO = new ItemDoPedidoRequestDto(item);
//            itens.add(itemDTO);
//        }
//    }
}
