package com.project.banking.utils.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition
        (
                info = @Info(
                        title = "Documentation Banking System API",
                        description = """
                                The Banking API is a back-end application developed using Spring Boot.\s
                                This application offers various features, including user authentication (registration and login),\s
                                opening and closing bank accounts, managing user profiles, administering bank branch data,\s
                                and facilitating both transfer and cash transactions. The API is designed to provide a secure\s
                                and efficient platform for handling essential banking operations.
                                """,
                        version = "1.0.0"
                ),
                servers = {
                        @Server(
                                url = "http://localhost:8080"
                        )
                }
        )
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Auth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SpringDocAPIConfiguration {
}
