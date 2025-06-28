package com.caio.PedidoProduto.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.caio.PedidoProduto.dto.PedidoDTO;
import com.caio.PedidoProduto.dto.PedidoResponseDTO;
import com.caio.PedidoProduto.dto.ProdutoDTO;
import com.caio.PedidoProduto.dto.UsuarioResponseDTO;
import com.caio.PedidoProduto.model.Pedido;
import com.caio.PedidoProduto.model.Produto;
import com.caio.PedidoProduto.model.Usuario;
import com.caio.PedidoProduto.repository.PedidoRepository;
import com.caio.PedidoProduto.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;

    public PedidoResponseDTO criarPedido(PedidoDTO dto, Usuario usuario) {
        List<Produto> produtos = produtoRepository.findAllById(dto.getProdutoIds());

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .produtos(produtos)
                .status("ABERTO")
                .dataCriacao(LocalDateTime.now())
                .build();

        Pedido salvo = pedidoRepository.save(pedido);

        return mapToResponse(salvo);
    }

    public Page<PedidoResponseDTO> listarPedidosDoUsuario(Usuario usuario, int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("dataCriacao").descending());
        Page<Pedido> pedidos = pedidoRepository.findByUsuario(usuario, pageable);

        return pedidos.map(this::mapToResponse);
    }

    private PedidoResponseDTO mapToResponse(Pedido pedido) {
        PedidoResponseDTO dto = modelMapper.map(pedido, PedidoResponseDTO.class);

     
        dto.setUsuario(modelMapper.map(pedido.getUsuario(), UsuarioResponseDTO.class));

        List<ProdutoDTO> produtos = pedido.getProdutos()
                .stream()
                .map(p -> modelMapper.map(p, ProdutoDTO.class))
                .collect(Collectors.toList());

        dto.setProdutos(produtos);

        return dto;
    }
}
