package com.github.apoioSolidario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title("Apoio Solidario API")
                        .description("A Apoio Solidario API é uma aplicação desenvolvida em Spring Boot, projetada para conectar instituições sem fins lucrativos (ONGs) a potenciais doadores. Esta API permite a criação e a gestão de ONGs, facilitando o gerenciamento dos dados associados a cada organização. A documentação da API é gerada automaticamente com o Swagger, proporcionando uma referência clara e acessível para os desenvolvedores.")
                        .contact(new Contact().name("José Ulisses").email("Contato.ulisses@Gmail.com")).version("1.0")
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")));
    }
}
