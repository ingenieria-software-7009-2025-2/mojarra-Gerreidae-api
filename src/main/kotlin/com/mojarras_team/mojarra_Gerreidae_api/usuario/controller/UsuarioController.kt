package com.mojarras_team.mojarra_Gerreidae_api.usuario.controller

import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserLogInBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserMeBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserUpdateBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.dominio.User
import com.mojarras_team.mojarra_Gerreidae_api.usuario.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/users")
class UsuarioController (private var usuarioServicio : UsuarioService) {

    @PostMapping()
    fun createUser(@RequestBody userBody : UserBody) : ResponseEntity<User>{

        val usuario = User(
        IDUsuario = userBody.idUsuario,
        Nombre = userBody.nombre,
        ApellidoP = userBody.apellidoP,
        ApellidoM = userBody.apellidoM,
        Correo = userBody.mail,
        Contrasenia = userBody.password,
        Token = null
        )
        val creado = usuarioServicio.crearUsuario(usuario)
        return ResponseEntity.ok(creado)
        
    }

    @PostMapping("/login")
    fun logInUser(@RequestBody userLogIn : UserLogInBody) : ResponseEntity<User>{
        val result = usuarioServicio.logInUsuario(userLogIn)
        return if (result != null)
            ResponseEntity.ok(result)
        else
            ResponseEntity.notFound().build()

    }

    @PostMapping("/logout")
    fun logOutUser() : ResponseEntity<String>{
        val salidaExitosa = usuarioServicio.logOutUsuario(userLogout)
        return if (salidaExitosa > 0)
            ResponseEntity.ok("La sesión ha finalizado")
        else
            ResponseEntity.badRequest().build()
    }

    @GetMapping("/me")
    fun meUser(@RequestHeader("Authorization") token : String, @RequestBody userMe : UserMeBody) : ResponseEntity<User>{

        val usuario = usuarioServicio.obtenerUsuario(token, userMe)
        return if (usuario != null)
            ResponseEntity.ok(usuario)
        else
            ResponseEntity.status(401).build()
        )
        return ResponseEntity.ok(miUsuario)
    }

    @PutMapping("/me")
    fun updateUser(@RequestHeader("Authorization") token : String, @RequestBody userUpdate : UserUpdateBody) : ResponseEntity<User> {

    }
}
