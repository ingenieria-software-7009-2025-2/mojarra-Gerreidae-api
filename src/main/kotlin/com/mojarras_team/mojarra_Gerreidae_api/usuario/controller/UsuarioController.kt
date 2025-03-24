package com.mojarras_team.mojarra_Gerreidae_api.usuario.controller

import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.*
import com.mojarras_team.mojarra_Gerreidae_api.usuario.dominio.User
import com.mojarras_team.mojarra_Gerreidae_api.usuario.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "usuario", description = "Administración de usuarios")
class UsuarioController (private var usuarioServicio : UsuarioService) {

    /**
     * Endpoint para crear un nuevo usuario
     * @param userBody Datos del usuario que recibe la petición
     * @return ResponseEntity con la respuesta del servicio
     */
    @PostMapping()
    @Operation(
        summary = "Registrar usuario",
        description = "Este endpoint permite registrar un nuevo usuario en la base de datos.",
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
        ApiResponse(responseCode = "409", description = "El usuario ya se encuentra registrado")
    ])
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
        try {
            val creado = usuarioServicio.crearUsuario(usuario)
            return ResponseEntity.ok(creado)
        } catch (e: IllegalStateException){
            return ResponseEntity.status(409).build()
        }
        
    }

    /**
     * Endpoint para iniciar sesión
     * @param UserLogInBody Datos del usuario (mail y password) para autenticación.
     * @return ResponseEntity con la información del usuario si la autenticación es exitosa, o 404 si falla.
     */
    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión de usuario",
        description = "Este endpoint permite a un usuario iniciar sesión utilizando su correo y contraseña."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Autenticación exitosa, usuario logueado."),
        ApiResponse(responseCode = "404", description = "Usuario no encontrado."),
        ApiResponse(responseCode = "401", description = "Credenciales incorrectas.")
    ])
    fun logInUser(@RequestBody userLogIn : UserLogInBody) : ResponseEntity<User>{
        try {
            val result = usuarioServicio.logInUsuario(userLogIn)
            return ResponseEntity.ok(result)
        } catch (e: NoSuchElementException) {
            return ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException){
            return ResponseEntity.status(401).build()
        }
    }

    /**
     * Endpoint para cerrar sesión.
     * Recibe un objeto UserLogoutBody (que contiene el id del usuario) y elimina el token.
     */
    @PostMapping("/logout")
    @Operation(
        summary = "Cerrar sesión de usuario",
        description = "Este endpoint permite a un usuario cerrar sesión eliminando su token de autenticación."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Sesión cerrada correctamente."),
        ApiResponse(responseCode = "400", description = "Error al cerrar sesión.")
    ])
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
    @Operation(
        summary = "Obtener información del usuario autenticado",
        description = "Este endpoint retorna la información del usuario asociado al token de autenticación."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Información del usuario obtenida correctamente."),
        ApiResponse(responseCode = "401", description = "Credenciales incorrectas.")
    ])
    fun meUser(@RequestHeader("Authorization") token: String): ResponseEntity<User> {
        try {
            val usuario = usuarioServicio.obtenerUsuario(token)
            return ResponseEntity.ok(usuario)
        } catch (e: IllegalArgumentException){
            return ResponseEntity.status(401).build()
        }
    }

    /**
     * Endpoint para actualizar la información del usuario autenticado.
     *
     * @param token Token de autorización
     * @param userUpdate Objeto de tipo UserUpdateBody que contiene los campos a actualizar para el usuario
     * @return ResponseEntity con el usuario actualizado o un estado 401 si no es válido.
     */
    @PutMapping("/me")
    @Operation(
        summary = "Editar usuario autenticado",
        description = "Este endpoint permite actualizar la información del usuario autenticado, utilizando el token para validación."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Información de usuario actualizada exitosamente."),
        ApiResponse(responseCode = "401", description = "Credenciales incorrectas.")
    ])
    fun updateUser(@RequestHeader("Authorization") token : String, @RequestBody userUpdate : UserUpdateBody) : ResponseEntity<User> {
        try {
            val usuarioActualizado = usuarioServicio.updateUsuario(token, userUpdate)
            return ResponseEntity.ok(usuarioActualizado)
        } catch (e: IllegalArgumentException){
            return ResponseEntity.status(401).build()
        }
    }
}
