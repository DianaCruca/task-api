package com.cruca.task_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "Task API",
                description = "This is an API for task management",
                termsOfService = "task-terms.example.com",
                version = "1.0.0",
                contact = @Contact(
                        name = "Diana Cruz",
                        email = "dian.cruca@gmail.com",
                        url = "task-contact-dev.example.com"
                ),
                license = @License(
                        name = "",
                        url = "task-license.example.com"

                )
        ),
        servers = {
                @Server(
                        description = "Local Server",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Dev Server",
                        url = "https://dev-task-test.example.com"
                ),
                @Server(
                        description = "Prod Server",
                        url = "https://task.example.com"
                )
        },
        security = @SecurityRequirement(
                name = "Security Token"
        )
)

@SecurityScheme(
        name = "Security Token",
        description = "Acces Token for my API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"

)
public class SwaggerConfig {}
