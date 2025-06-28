package com.caio.PedidoProduto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.caio.PedidoProduto.dto.AuthRequestDto;
import com.caio.PedidoProduto.dto.AuthResponseDto;
import com.caio.PedidoProduto.dto.RegisterDto;
import com.caio.PedidoProduto.exception.DuplicatedUserNameException;
import com.caio.PedidoProduto.model.Usuario;
import com.caio.PedidoProduto.provider.TokenProvider;
import com.caio.PedidoProduto.repository.UsuarioRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    public AuthResponseDto login(AuthRequestDto authRequest){
        var userNamePasswd = new UsernamePasswordAuthenticationToken(authRequest.nome(), authRequest.password());
        var auth = authenticationManager.authenticate(userNamePasswd);
        var token = tokenProvider.getToken((Usuario) auth.getPrincipal());
        return new AuthResponseDto(authRequest.nome(), token);
    }

    public Usuario register(RegisterDto registerData){
        var found = userDetailsService.loadUserByUsername(registerData.nome());
        if (found != null){
            throw new DuplicatedUserNameException("Nome de usuário indisponível!");
        } else {
            String encPasswd = new BCryptPasswordEncoder().encode(registerData.senha());
            Usuario userModel = new Usuario();
            userModel.setNome(registerData.nome());
            userModel.setEmail(registerData.email());
            //..encrypted password
            userModel.setSenha(encPasswd);
            return userRepository.save(userModel);
        }
    }


}
