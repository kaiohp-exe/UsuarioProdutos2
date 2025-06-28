package com.caio.PedidoProduto.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caio.PedidoProduto.dto.ProdutoDTO;
import com.caio.PedidoProduto.service.ProdutoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDTO> criar(@RequestBody ProdutoDTO dto) {
        ProdutoDTO novoProduto = produtoService.criarProduto(dto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        ProdutoDTO atualizado = produtoService.atualizarProduto(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO dto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {

        Page<ProdutoDTO> produtos = produtoService.listarProdutos(pagina, tamanho);
        return ResponseEntity.ok(produtos);
    }
}
