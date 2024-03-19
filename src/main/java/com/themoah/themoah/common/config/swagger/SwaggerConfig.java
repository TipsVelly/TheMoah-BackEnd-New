package com.themoah.themoah.common.config.swagger;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
    info = @Info(
        title = "TheMoah Service API 명세서",
        description = "TheMoah client 참고 API 명세서",
        version = "v1"
    )
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    
    @Value("${jwt.scheme}")
    private String prefixType;

    @Value("${jwt.header}")
    private String headerName;

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
            .type(Type.HTTP)
            .scheme(prefixType)
            .bearerFormat("JWT")
            .in(In.HEADER)
            .name(headerName);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
            .components(
                new Components().addSecuritySchemes("bearerAuth", securityScheme)
            ).security(Arrays.asList(securityRequirement));
    }
}
