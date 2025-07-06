package com.erp.acmf_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI acmfApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ACMF API - Documentação")
                        .description("Documentação da API REST da aplicação ACMF, com todos os endpoints disponíveis.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Dev")
                                .email("acmf_gov@protonmail.com")
                                .url("http://31.97.57.75"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}
