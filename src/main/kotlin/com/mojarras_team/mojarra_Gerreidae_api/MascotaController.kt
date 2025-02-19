package com.mojarras_team.mojarra_Gerreidae_api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/mascota")
class MascotaController {
    @GetMapping
    fun retrieveHealth(): ResponseEntity<Mascota>{
        val miMascota = Mascota(tipo = "perro", name = "Pelusa", peso="6kg")
        return ResponseEntity.ok(miMascota)
    }

    @PostMapping
    fun createMascota(@RequestBody mascotaBody: MascotaBody): ResponseEntity<Mascota>{

        val miMascota = Mascota(
            tipo = mascotaBody.tipo,
            name = mascotaBody.name,
            peso = mascotaBody.peso
        )
        return ResponseEntity.ok(miMascota)
    }
}