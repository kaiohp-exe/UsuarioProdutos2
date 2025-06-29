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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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

    @Operation(
        summary = "Cria um novo pedido para o usuário autenticado",
        requestBody = @RequestBody(
            description = "DTO contendo os dados do pedido a ser criado",
            required = true,
            content = @Content(schema = @Schema(implementation = PedidoDTO.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso",
                         content = @Content(schema = @Schema(implementation = PedidoResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
        }
    )
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@org.springframework.web.bind.annotation.RequestBody PedidoDTO dto,
                                                         HttpServletRequest request) {
        String token = tokenProvider.getTokenFromHttpRequest(request);
        String email = tokenProvider.getDecodedToken(token).getSubject(); // JWT subject = email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PedidoResponseDTO response = pedidoService.criarPedido(dto, usuario);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Lista pedidos paginados do usuário autenticado",
        parameters = {
            @Parameter(name = "pagina", description = "Número da página (começa em 0)", example = "0"),
            @Parameter(name = "tamanho", description = "Quantidade de itens por página", example = "10")
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista paginada de pedidos",
                         content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
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
