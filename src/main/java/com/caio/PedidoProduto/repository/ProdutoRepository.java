package com.caio.PedidoProduto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.caio.PedidoProduto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
