package com.caio.PedidoProduto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
}
