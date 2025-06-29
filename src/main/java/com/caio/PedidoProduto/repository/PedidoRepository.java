package com.caio.PedidoProduto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.caio.PedidoProduto.model.Pedido;
import com.caio.PedidoProduto.model.Usuario;



public interface    PedidoRepository extends JpaRepository<Pedido,Long>{
    Page<Pedido> findByUsuario(Usuario usuario, Pageable pageable);
}
