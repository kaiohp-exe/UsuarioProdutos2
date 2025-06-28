package com.caio.PedidoProduto.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.caio.PedidoProduto.exception.InvalidJwtAuthenticationException;
import com.caio.PedidoProduto.model.Usuario;
import com.caio.PedidoProduto.service.UserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenProvider {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-in-hours}")
    private int jwtExpiresInHours;

    @Value("${security.jwt.time-zone}")
    private String timeZone;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Autowired
    private UserDetailsService userDetailsService;


    public String getToken(Usuario userModel){
        Algorithm alg = Algorithm.HMAC256(secret);
        try {   
            String jwt = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(userModel.getEmail()) // <- AQUI
                    .withExpiresAt(Date.from(getTokenExpireTime()))
                    .sign(alg);
            return jwt;
        } catch (JWTCreationException e){
            throw new JWTCreationException("Erro ao criar o JWT: ", e);
        }
    }

    private Instant getTokenExpireTime() {
        return LocalDateTime.now().plusHours(jwtExpiresInHours).toInstant(ZoneOffset.of(timeZone));
    }

    public boolean isTokenValid(String token){
        System.out.println("isTokenValid: "+ token);
        DecodedJWT decodedJWT = getDecodedToken(token);
        try{
            if(decodedJWT.getExpiresAt().before(new Date())){
                return false;
            }
            return true;
        } catch (Exception e){
            throw new InvalidJwtAuthenticationException("Token expirado ou inválido!");
        }
    }

    public DecodedJWT getDecodedToken(String token){
        Algorithm alg = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        return verifier.verify(token);
    }

    public String getTokenFromHttpRequest(HttpServletRequest request){
        var header = request.getHeader("Authorization");
        System.out.println("GetTokenFromHttpRequest==> " + request.getRequestURI());
        return header == null ? null : header.replace("Bearer ", "");
    }

    public Authentication getAuthentication(String token){
        DecodedJWT decodedJWT = getDecodedToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


}
