package com.caio.PedidoProduto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caio.PedidoProduto.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    // Optional<Usuario> findByEmail(String email);
    Usuario findByNome(String nome);
}
