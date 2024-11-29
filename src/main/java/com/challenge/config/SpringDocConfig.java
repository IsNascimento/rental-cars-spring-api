package com.challenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Rental Cars Spring API")
                                .version("1.0.0")
                                .description("API Desenvolvida para o desafio da Stefanini - Vaga Desenvolvedor JAVA")
                                .license(
                                        new License().name("Apache 2.0").url("http://springdoc.org")
                                )
                )
                .addTagsItem(new Tag().name("Carros").description("Operações relacionadas a carros"))
                .addTagsItem(new Tag().name("Aluguel").description("Operações relacionadas a aluguel de carros"));
    }


}
