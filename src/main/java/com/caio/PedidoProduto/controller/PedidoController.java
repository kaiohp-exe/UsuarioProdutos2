package com.caio.PedidoProduto.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.caio.PedidoProduto.dto.PedidoDTO;
import com.caio.PedidoProduto.dto.PedidoResponseDTO;
import com.caio.PedidoProduto.model.Usuario;
import com.caio.PedidoProduto.provider.TokenProvider;
import com.caio.PedidoProduto.repository.UsuarioRepository;
import com.caio.PedidoProduto.service.PedidoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para criação e listagem de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final TokenProvider tokenProvider;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoDTO dto, HttpServletRequest request) {
        String token = tokenProvider.getTokenFromHttpRequest(request);
        String email = tokenProvider.getDecodedToken(token).getSubject(); // JWT subject = email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PedidoResponseDTO response = pedidoService.criarPedido(dto, usuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> listarPedidos(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {

        String token = tokenProvider.getTokenFromHttpRequest(request);
        String email = tokenProvider.getDecodedToken(token).getSubject(); // JWT subject = email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Page<PedidoResponseDTO> pedidos = pedidoService.listarPedidosDoUsuario(usuario, pagina, tamanho);
        return ResponseEntity.ok(pedidos);
    }
}
