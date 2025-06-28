package com.caio.PedidoProduto.service;


import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.caio.PedidoProduto.dto.ProdutoDTO;
import com.caio.PedidoProduto.model.Produto;
import com.caio.PedidoProduto.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;

    public ProdutoDTO criarProduto(ProdutoDTO dto){
        Produto produto = modelMapper.map(dto, Produto.class);
        Produto salvo = produtoRepository.save(produto);
        return modelMapper.map(salvo, ProdutoDTO.class);
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO dto){
        Produto produto = produtoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());

        Produto atualizado = produtoRepository.save(produto);
        return modelMapper.map(atualizado, ProdutoDTO.class);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public Page<ProdutoDTO> listarProdutos(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("nome").ascending());
        Page<Produto> produtosPage = produtoRepository.findAll(pageable);
        return produtosPage.map(produto -> modelMapper.map(produto, ProdutoDTO.class));
    }
}