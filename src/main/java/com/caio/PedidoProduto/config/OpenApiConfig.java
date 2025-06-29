package com.caio.PedidoProduto.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Minha API de Produtos")
                .version("v1")
                .description("API para gerenciamento de produtos e pedidos"));
    }
    @Bean
    public GroupedOpenApi produtosApi() {
        return GroupedOpenApi.builder()
                .group("produtos")
                .pathsToMatch("/produtos/**")
                .build();
    }
    
    @Bean
    public GroupedOpenApi pedidosApi() {
        return GroupedOpenApi.builder()
                .group("pedidos")
                .pathsToMatch("/pedidos/**")
                .build();
    }
    
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("autenticacao")
                .pathsToMatch("/auth/**")
                .build();
    }
}