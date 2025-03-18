package com.mojarras_team.mojarra_Gerreidae_api

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        // Configura CORS para todas las rutas
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:4200")  // Orígenes permitidos
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Métodos permitidos
            .allowedHeaders("Authorization", "Content-Type")  // Encabezados permitidos
            .allowCredentials(true)  // Permite credenciales (cookies, cabeceras de autenticación, etc.)
            .maxAge(3600)  // El navegador puede almacenar la respuesta de pre-vuelo durante 1 hora
    }
}
