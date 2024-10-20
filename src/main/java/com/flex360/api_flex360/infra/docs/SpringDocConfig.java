package com.flex360.api_flex360.infra.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@OpenAPIDefinition(

    info = @Info(
        title = "Documentaçao da api da flex360"
    ),
    
    security = {
        @SecurityRequirement(name = "BearerAuth")
    }
)
@SecurityScheme(
    name = "BearerAuth",
    description = "Token JWT",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class SpringDocConfig {

}
