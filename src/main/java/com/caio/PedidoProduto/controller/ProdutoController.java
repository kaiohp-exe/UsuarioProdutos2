package com.caio.PedidoProduto.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.caio.PedidoProduto.dto.ProdutoDTO;
import com.caio.PedidoProduto.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Operation(
        summary = "Cria um novo produto",
        requestBody = @RequestBody(
            description = "DTO do produto a ser criado",
            required = true,
            content = @Content(schema = @Schema(implementation = ProdutoDTO.class))
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
        }
    )
    @PostMapping
    public ResponseEntity<ProdutoDTO> criar(@org.springframework.web.bind.annotation.RequestBody ProdutoDTO dto) {
        ProdutoDTO novoProduto = produtoService.criarProduto(dto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Atualiza um produto existente",
        parameters = {
            @Parameter(name = "id", description = "ID do produto a ser atualizado", required = true)
        },
        requestBody = @RequestBody(
            description = "DTO com os dados para atualizar o produto",
            required = true,
            content = @Content(schema = @Schema(implementation = ProdutoDTO.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody ProdutoDTO dto) {
        ProdutoDTO atualizado = produtoService.atualizarProduto(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(
        summary = "Busca um produto pelo ID",
        parameters = {
            @Parameter(name = "id", description = "ID do produto a ser buscado", required = true)
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado", content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO dto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Deleta um produto pelo ID",
        parameters = {
            @Parameter(name = "id", description = "ID do produto a ser deletado", required = true)
        },
        responses = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Lista produtos com paginação",
        parameters = {
            @Parameter(name = "page", description = "Número da página (começa em 0)", example = "0"),
            @Parameter(name = "size", description = "Tamanho da página", example = "10")
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos paginada")
        }
    )
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
    
        Page<ProdutoDTO> produtos = produtoService.listarProdutos(page, size);
        return ResponseEntity.ok(produtos);
    }
}
