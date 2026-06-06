package com.example.ecommerce.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce API Documentation")
                        .version("1.0.0")
                        .description("This API documentation covers all endpoints for Users, Products, Carts, and Orders management in the E-Commerce platform.")
                        .contact(new Contact()
                                .name("Divyansh")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}