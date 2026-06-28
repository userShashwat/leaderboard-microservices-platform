package com.leaderboard.submissionservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI submissionAPI() {

        final String securityScheme = "bearerAuth";

        return new OpenAPI()

                .info(
                        new Info()
                                .title("Submission Service API")
                                .version("1.0.0")
                                .description("Contest, Problem and Submission APIs")
                                .contact(
                                        new Contact()
                                                .name("shashwat")
                                                .email("shashwatsharma274@gmail.com")
                                )
                )

                .externalDocs(
                        new ExternalDocumentation()
                                .description("Leaderboard Project")
                                .url("https://github.com/userShashwat/leaderboard-microservices-platform")
                )

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securityScheme)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securityScheme,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
