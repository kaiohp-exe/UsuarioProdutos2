package com.caio.PedidoProduto.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
   private Long id;
    private List<Long> produtoIds;
    private String status;
    private LocalDateTime dataCriacao;
}