package com.leaderboard.userservice.config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    final String securitySchemeName = "bearerAuth";
    @Bean
    public OpenAPI userServiceOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("User Service API")
                                .description("User Authentication and Authorization APIs")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("Shashwat")
                                                .email("shashwatsharma274@example.com")
                                )
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Project Documentation")
                                .url("https://github.com/userShashwat/leaderboard-microservices-platform")
                )
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
/*
  host: ${REDIS_HOST:localhost}
  port: ${REDIS_PORT:6379}

  bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}


  defaultZone: ${EUREKA_SERVER:http://localhost:8761/eureka/}
 */
