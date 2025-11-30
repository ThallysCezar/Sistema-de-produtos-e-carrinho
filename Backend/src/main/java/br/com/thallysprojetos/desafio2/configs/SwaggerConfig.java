package br.com.thallysprojetos.desafio2.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Desafio 2 do Grupo Moura, Backend")
                        .version("1.0")
                        .description("Um pequeno sistemas de produtos com carrinho de compras e cálculo automático de total. "
                                + "O código-fonte do projeto esta disponivel no GitHub: "
                                + "https://github.com/ThallysCezar/Sistema-de-produtos-e-carrinho")
        );
    }

}