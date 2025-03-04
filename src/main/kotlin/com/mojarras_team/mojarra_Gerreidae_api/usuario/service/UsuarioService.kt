package com.mojarras_team.mojarra_Gerreidae_api.usuario.service

import com.mojarras_team.mojarra_Gerreidae_api.usuario.dominio.User
import com.mojarras_team.mojarra_Gerreidae_api.repository.UserRepository
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserLogInBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserLogoutBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserMeBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserUpdateBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.repository.entity.UserEntity
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.full.memberProperties

/**
 * Clase para manejar la lógica del las peticiones HTTP relacionadas
 * a la tabla Usuario.
 */
@Service
class UsuarioService (private var usuarioRepo : UserRepository) {

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuario Nuevo usuario a agregar.
     * @return El susuario que se creó.
     */
    fun crearUsuario(usuario : User) : User {

        val nuevoUsuarioDB = UserEntity(
            idUsuario = 0,
            nombre = usuario.Nombre,
            apellidoP = usuario.ApellidoP,
            apellidoM = usuario.ApellidoM,
            correo = usuario.Correo,
            contrasenia = usuario.Contrasenia,
            token = UUID.randomUUID().toString()
        )
        val result = usuarioRepo.save(nuevoUsuarioDB)

        val usuarioCreado = User (
            IDUsuario = result.idUsuario,
            Nombre = result.nombre,
            ApellidoP = result.apellidoP,
            ApellidoM = result.apellidoM,
            Correo = result.correo,
            Contrasenia = result.contrasenia,
            Token = result.token
        )
        return usuarioCreado
    }

    /**
     * Función para obtener los datos de un usuario mediante su id y un token.
     * Es necesario que el token corresponda al del usuario que se quiere consultar.
     *
     * @param token un token para verificar que se haya iniciado sesión.
     * @param usuarioMeBody objeto que contiene el id del usuario que se quiere consultar.
     * @return la información del usuario consultado o null si el token no es válido.
     */
    fun obtenerUsuario(token : String, usuarioMeBody : UserMeBody) : User? {

        val result = usuarioRepo.findById(usuarioMeBody.idUsuario.toInt())

        if (result.isEmpty){
            throw IllegalArgumentException("Este usuario no existe.")
        }
        val usuario = result.get()
        if (usuario.token == token){
            return User (
                IDUsuario = usuario.idUsuario,
                Nombre = usuario.nombre,
                ApellidoP = usuario.apellidoP,
                ApellidoM = usuario.apellidoM,
                Correo = usuario.correo,
                Contrasenia = usuario.contrasenia,
                Token = usuario.token
            )
        }
        return null
    }

    /**
     * Función para hacer el login de un usuario por medeio de su
     * correo y contraseña.
     *
     * @param usuarioLogInBody objeto con el correo y contraseña.
     * @return el usuario que realizó el login o null si la contraseña es incorrecta.
     */
    fun logInUsuario(usuarioLogInBody : UserLogInBody) : User? {
        val usuario = usuarioRepo.findByMail(usuarioLogInBody.mail)
            ?: throw IllegalArgumentException("Este usuario no existe.")
        if (usuario.contrasenia == usuarioLogInBody.password){
            return User(
                IDUsuario = usuario.idUsuario,
                Nombre = usuario.nombre,
                ApellidoP = usuario.apellidoP,
                ApellidoM = usuario.apellidoM,
                Correo = usuario.correo,
                Contrasenia = usuario.contrasenia,
                Token = usuario.token
            )
        }
        return null
    }

    /**
     * Función para hacer el logout del usuario mediante su id.
     *
     * @param usuarioLogOutBody coneirne el id.
     * @return el número de filas que fueron modificadas en la tabla (1 si logout exitoso).
     */
    fun logOutUsuario(usuarioLogOutBody : UserLogoutBody) : Int {
        val usuarioObtenido = usuarioRepo.deleteToken(usuarioLogOutBody.idUsuario)
        return usuarioObtenido
    }

    /**
     * Función para actualizar un usuario mediante su id.
     * Se verifica su token.
     *
     * @param token el token del usuario a modificar.
     * @param usuarioUpdateBody objeto con el id del usuario a actualizar.
     */
    fun updateUsuario(token: String, usuarioUpdateBody : UserUpdateBody) : User? {
        val usuarioObtenido = usuarioRepo.findById(usuarioUpdateBody.idUsuario.toInt()).getOrNull()
            ?: throw IllegalArgumentException("Este usuario no existe")

        if(usuarioObtenido.token == null || usuarioObtenido.token != token){
            return null
        }

        val usuarioObtenidoC = User (
            IDUsuario = usuarioObtenido.idUsuario,
            Nombre = usuarioObtenido.nombre,
            ApellidoP = usuarioObtenido.apellidoP,
            ApellidoM = usuarioObtenido.apellidoM,
            Correo = usuarioObtenido.correo,
            Contrasenia = usuarioObtenido.contrasenia,
            Token = usuarioObtenido.token
        )

        val valores = mutableMapOf<String, String>()
        for (prop in User::class.memberProperties){
            valores[prop.name] = prop.get(usuarioObtenidoC).toString() ?: ""
        }
        for (prop in UserUpdateBody::class.memberProperties){
            val nombreCampo = prop.name
            val valorCampo = prop.get(usuarioUpdateBody).toString() ?: ""
            if (valorCampo == ""){
                continue
            }else {
                valores[nombreCampo] = valorCampo
            }

        }

        val usuarioActualizado = UserEntity(
            idUsuario = valores["IDUsuario"]?.toInt() ?: 0,
            nombre = valores["Nombre"] ?: "No alcanzable",
            apellidoP = valores["ApellidoP"] ?: "No alcanzable",
            apellidoM = valores["ApellidoM"] ?: "No alcanzable",
            correo = valores["ApellidoM"] ?: "No alcanzable",
            contrasenia = valores["Contrasenia"] ?: "No alcanzable",
            token = valores["Token"] ?: "No alcanzable"
        )

        val resultado = usuarioRepo.save(usuarioActualizado)

        val resultadoUsuarioAct = User (
            IDUsuario = resultado.idUsuario,
            Nombre = resultado.nombre,
            ApellidoP = resultado.apellidoP,
            ApellidoM = resultado.apellidoM,
            Correo = resultado.correo,
            Contrasenia = resultado.contrasenia,
            Token = resultado.token
        )
        return  resultadoUsuarioAct
    }
}