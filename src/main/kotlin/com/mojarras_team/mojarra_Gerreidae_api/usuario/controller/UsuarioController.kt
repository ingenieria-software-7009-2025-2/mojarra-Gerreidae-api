package com.mojarras_team.mojarra_Gerreidae_api.usuario.controller

import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserLogInBody
import com.mojarras_team.mojarra_Gerreidae_api.models.User
import com.mojarras_team.mojarra_Gerreidae_api.usuario.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/users")
class UsuarioController {

    @PostMapping()
    fun createUser(@RequestBody userBody : UserBody) : ResponseEntity<User>{
        val miUsuario : User = User(
            mail = userBody.mail,
            password = userBody.password,
            id = userBody.idUsuario,
            nombre = userBody.nombre + userBody.apellidoP + userBody.apellidoM
        )
        return ResponseEntity.ok(miUsuario)
    }

    @PostMapping
    fun addUser(@RequestBody userBody: UserBody): ResponseEntity<Any> {
        // Convertir los datos del request a un objeto del dominio

    }

    @PostMapping("/login")
    fun logInUser(@RequestBody userLogIn : UserLogInBody) : ResponseEntity<User>{
        val result = UsuarioService.login(UserLogInBody.mail, UserLogInBody.password)

    }

    @PostMapping("/logout")
    fun logOutUser() : ResponseEntity<String>{
        return ResponseEntity.ok("Sesión Cerrada")
    }

    @GetMapping("/me")
    fun meUser() : ResponseEntity<User>{
        val miUsuario : User = User(
            mail = "pepe@mail.com",
            password = "123",
            token = "456"
        )
        return ResponseEntity.ok(miUsuario)
    }
}