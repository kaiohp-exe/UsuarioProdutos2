package com.caio.PedidoProduto.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.caio.PedidoProduto.dto.AuthRequestDto;
import com.caio.PedidoProduto.dto.AuthResponseDto;
import com.caio.PedidoProduto.dto.RegisterDto;
import com.caio.PedidoProduto.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para login e cadastro de usuários")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Operation(
        summary = "Realiza login do usuário",
        description = "Recebe credenciais de login e retorna um token JWT e nome do usuário em caso de sucesso.",
        requestBody = @RequestBody(
            description = "Dados de autenticação do usuário",
            required = true,
            content = @Content(schema = @Schema(implementation = AuthRequestDto.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                         content = @Content(schema = @Schema(implementation = AuthResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@org.springframework.web.bind.annotation.RequestBody @Valid AuthRequestDto authRequestDto){
        AuthResponseDto authResponseDto = authenticationService.login(authRequestDto);
        return ResponseEntity.ok(new AuthResponseDto(
                authResponseDto.userName(), authResponseDto.token()
        ));
    }

    @Operation(
        summary = "Registra um novo usuário",
        description = "Cria um novo usuário no sistema com os dados fornecidos.",
        requestBody = @RequestBody(
            description = "Dados para registro de um novo usuário",
            required = true,
            content = @Content(schema = @Schema(implementation = RegisterDto.class))
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para registro")
        }
    )
    @PostMapping("/register")
    public ResponseEntity<Void> register(@org.springframework.web.bind.annotation.RequestBody @Valid RegisterDto registerDto) {
        authenticationService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
