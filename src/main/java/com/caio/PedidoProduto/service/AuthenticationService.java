package com.caio.PedidoProduto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

public AuthResponseDto login(AuthRequestDto authRequest) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.senha());
    var auth = authenticationManager.authenticate(usernamePassword);

    var userDetails = (UserDetails) auth.getPrincipal();

     var usuario = userRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    var token = tokenProvider.getToken(usuario);

    return new AuthResponseDto(usuario.getNome(), token);
}


    public Usuario register(RegisterDto registerData){
     if (userRepository.findByEmail(registerData.email()).isPresent()) {
        throw new DuplicatedUserNameException("Email já cadastrado!");
    }
        String encPasswd = new BCryptPasswordEncoder().encode(registerData.senha());
        Usuario userModel = new Usuario();
        userModel.setNome(registerData.nome());
        userModel.setEmail(registerData.email());
        userModel.setSenha(encPasswd);

        return userRepository.save(userModel);
    }


}
