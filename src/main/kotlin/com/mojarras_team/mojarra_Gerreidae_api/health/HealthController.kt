package com.mojarras_team.mojarra_Gerreidae_api.modules.config.framework.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/health")
class HealthController {
    @GetMapping
    fun retrieveHealth(): ResponseEntity<String> {
        return ResponseEntity.ok("HOLA MUNDO :DDD")
    }
}