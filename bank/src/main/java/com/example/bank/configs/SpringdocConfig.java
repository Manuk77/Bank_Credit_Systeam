package com.example.bank.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The `SpringdocConfig` class is a configuration class for OpenAPI documentation using Springdoc.
 * It defines a bean for the base OpenAPI configuration.
 */
@OpenAPIDefinition
@Configuration
public class SpringdocConfig {

    /**
     * Creates a bean for the base OpenAPI configuration.
     *
     * @return The base OpenAPI configuration.
     */
    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Bank Credit System")
                .version("1.0.0")
                .description("Controller"));
    }

}
