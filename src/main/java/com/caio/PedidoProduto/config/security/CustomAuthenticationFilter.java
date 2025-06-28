package com.caio.PedidoProduto.config.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.caio.PedidoProduto.provider.TokenProvider;

import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = tokenProvider.getTokenFromHttpRequest(request);
        String path = request.getRequestURI();
        System.out.println("URI Requisitado:::" + path);



        if (token != null && tokenProvider.isTokenValid(token)) {
            Authentication auth = tokenProvider.getAuthentication(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("Tentou validar o Token....!");
            }
        }
        //continues the flow filter
        filterChain.doFilter(request, response);
    }

}

