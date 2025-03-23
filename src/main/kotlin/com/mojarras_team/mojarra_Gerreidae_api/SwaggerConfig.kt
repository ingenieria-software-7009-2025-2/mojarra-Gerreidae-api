package com.mojarras_team.mojarra_Gerreidae_api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import java.net.http.HttpHeaders

@OpenAPIDefinition(
    info = Info(
        title = "Mojarra Drive",
        description = "Mojarra Drive es un proyecto que busca facilitar"+
            " el registro y seguimiento de incidentes urbanos,"+
            " tales como baches, mal estado de la infraestructura,"+
            " accidentes, y otros problemas que afectan la calidad"+
            " de vida en las ciudades. Los usuarios pueden acceder"+
            " al sistema para registrar incidentes, proporcionando"+
            " detalles como la ubicación, tipo de incidente, descripción"+
            " y fotos.",
        version = "1.0.0",
        summary = "Bienvenido a la documentación oficial de la API de Mojarra Drive."
    ),
    servers = [Server( url = "http://localhost:8080" )],
    security = [SecurityRequirement( name = "Security Token" )]
)
@SecurityScheme(
    name = "Security Token",
    description = "Token asignado por la API a un usuario",
    type = SecuritySchemeType.HTTP,
    paramName = org.springframework.http.HttpHeaders.AUTHORIZATION,
    `in` = SecuritySchemeIn.HEADER,
    scheme = "Bearer"
)
class SwaggerConfig {}