package com.caio.PedidoProduto.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caio.PedidoProduto.dto.AuthRequestDto;
import com.caio.PedidoProduto.dto.AuthResponseDto;
import com.caio.PedidoProduto.dto.RegisterDto;
import com.caio.PedidoProduto.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthRequestDto authRequestDto){
        AuthResponseDto authResponseDto = authenticationService.login(authRequestDto);
        return ResponseEntity.ok(new AuthResponseDto(
                authResponseDto.userName(), authResponseDto.token()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto registerDto) {
        authenticationService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
