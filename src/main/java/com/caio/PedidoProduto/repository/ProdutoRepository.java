package com.caio.PedidoProduto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caio.PedidoProduto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
