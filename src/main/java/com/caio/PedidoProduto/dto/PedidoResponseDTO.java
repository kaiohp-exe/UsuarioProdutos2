package com.caio.PedidoProduto.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime dataCriacao;
    private String status;
    private UsuarioResponseDTO usuario;
    private List<ProdutoDTO> produtos;
}