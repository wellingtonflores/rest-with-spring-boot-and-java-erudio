package com.erudio.rest_with_spring_boot_and_java_erudio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("RESTful API with Java 21 and Spring Boot")
                        .version("v1")
                        .description("API para gerenciar endpoints básicos de pessoas e livros")
                        .termsOfService("Some term of services")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("Some URL"))
                );
    }
}
