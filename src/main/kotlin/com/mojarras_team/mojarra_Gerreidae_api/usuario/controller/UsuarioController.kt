package com.mojarras_team.mojarra_Gerreidae_api.usuario.controller

import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.*
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


    /**
     * Endpoint para crear un nuevo usuario
     * @param userBody Datos del usuario que recibe la petición
     * @return ResponseEntity con la respuesta del servicio
     */
    @PostMapping()
    fun createUser(@RequestBody userBody : UserBody) : ResponseEntity<User>{

        val usuario = User(
        idUsuario = userBody.idUsuario,
        nombre = userBody.nombre,
        apellidoP = userBody.apellidoP,
        apellidoM = userBody.apellidoM,
        mail = userBody.mail,
        password = userBody.password,
        token = null
        )
        val creado = usuarioServicio.crearUsuario(usuario)
        return ResponseEntity.ok(creado)
        
    }


    /**
     * Endpoint para iniciar sesión
     * @param UserLogInBody Datos del usuario (mail y password) para autenticación.
     * @return ResponseEntity con la información del usuario si la autenticación es exitosa, o 404 si falla.
     */
    @PostMapping("/login")
    fun logInUser(@RequestBody userLogIn : UserLogInBody) : ResponseEntity<Any>{
        val result = usuarioServicio.logInUsuario(userLogIn)
        return if (result != null)
            ResponseEntity.ok(result)
        else
            ResponseEntity.notFound().build()

    }

    /**
     * Endpoint para cerrar sesión.
     * Recibe un objeto UserLogoutBody (que contiene el id del usuario) y elimina el token.
     */
    @PostMapping("/logout")
    fun logOutUser(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        val salidaExitosa = usuarioServicio.logOutUsuario(token)
        return if (salidaExitosa > 0)
            ResponseEntity.ok("La sesión ha finalizado")
        else
            ResponseEntity.badRequest().build()
    }

    /**
     * Endpoint para obtener la información del usuario autenticado.
     * @param token Token de autorización.
     * @return ResponseEntity con la información del usuario o un estado 401 si no es válido.
     */
    @GetMapping("/me")
    fun meUser(@RequestHeader("Authorization") token: String): ResponseEntity<User> {
        val usuario = usuarioServicio.obtenerUsuario(token)
        return if (usuario != null)
            ResponseEntity.ok(usuario)
        else
            ResponseEntity.status(401).build()
    }

    /**
     * Endpoint para actualizar la información del usuario autenticado.
     *
     * @param token Token de autorización
     * @param userUpdate Objeto de tipo UserUpdateBody que contiene los campos a actualizar para el usuario
     * @return ResponseEntity con el usuario actualizado o un estado 401 si no es válido.
     */
    @PutMapping("/me")
    fun updateUser(@RequestHeader("Authorization") token : String, @RequestBody userUpdate : UserUpdateBody) : ResponseEntity<User> {
        val usuarioActualizado = usuarioServicio.updateUsuario(token, userUpdate)
        return if (usuarioActualizado != null)
            ResponseEntity.ok(usuarioActualizado)
        else
            ResponseEntity.status(401).build()
    }
}
