package com.mojarras_team.mojarra_Gerreidae_api.usuario.service

import com.mojarras_team.mojarra_Gerreidae_api.usuario.dominio.User
import com.mojarras_team.mojarra_Gerreidae_api.usuario.repository.UserRepository
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserLogInBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserUpdateBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.repository.entity.UserEntity
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

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
     * @return El usuario que se creó.
     */
    fun crearUsuario(usuario : User) : User {
        if(usuarioRepo.findByMail(usuario.mail) != null){
            throw IllegalStateException("Este usuario ya se encuentra registrado.")
        }
        var tokenNoRepetido: String
        do {
            tokenNoRepetido = UUID.randomUUID().toString()
        } while (usuarioRepo.findByToken(tokenNoRepetido) != null)

        val nuevoUsuarioDB = UserEntity(
            idUsuario = 0,
            nombre = usuario.nombre,
            apellidoP = usuario.apellidoP,
            apellidoM = usuario.apellidoM,
            mail = usuario.mail,
            password = usuario.password,
            token = tokenNoRepetido,
            esAdministrador = usuario.esAdministrador
        )
        val result = usuarioRepo.save(nuevoUsuarioDB)

        val usuarioCreado = User (
            idUsuario = result.idUsuario,
            nombre = result.nombre,
            apellidoP = result.apellidoP,
            apellidoM = result.apellidoM,
            mail = result.mail,
            password = result.password,
            token = result.token,
            esAdministrador = result.esAdministrador
        )
        return usuarioCreado
    }

    /**
     * Función para obtener los datos de un usuario mediante su id y un token.
     * Es necesario que el token corresponda al del usuario que se quiere consultar.
     *
     * @param token un token para verificar que se haya iniciado sesión.
     * @return la información del usuario consultado o null si el token no es válido.
     */
    fun obtenerUsuario(token : String) : User {

        val usuario = usuarioRepo.findByToken(token)

        return if (usuario != null){
            User (
                idUsuario = usuario.idUsuario,
                nombre = usuario.nombre,
                apellidoP = usuario.apellidoP,
                apellidoM = usuario.apellidoM,
                mail = usuario.mail,
                password = usuario.password,
                token = usuario.token,
                esAdministrador = usuario.esAdministrador
            )
        } else {
            throw IllegalArgumentException("El token es inválido para este usuario.")
        }
    }

    /**
     * Función para hacer el login de un usuario por medeio de su
     * mail y password.
     *
     * @param usuarioLogInBody objeto con el mail y password.
     * @return el usuario que realizó el login o null si la password es incorrecta.
     */
    fun logInUsuario(usuarioLogInBody : UserLogInBody) : User {
        val usuario = usuarioRepo.findByMail(usuarioLogInBody.mail)?: throw NoSuchElementException("Este usuario no existe.")

        var token: String
        do {
            token = UUID.randomUUID().toString()
        } while (usuarioRepo.findByToken(token) != null)
        usuario.token = token
        usuarioRepo.save(usuario)

        if (usuario.password == usuarioLogInBody.password){
            return User(
                idUsuario = usuario.idUsuario,
                nombre = usuario.nombre,
                apellidoP = usuario.apellidoP,
                apellidoM = usuario.apellidoM,
                mail = usuario.mail,
                password = usuario.password,
                token = usuario.token,
                esAdministrador = usuario.esAdministrador
            )
        }
        throw IllegalArgumentException("La contraseña es incorrecta.")
    }

    /**
     * Función para hacer el logout del usuario mediante su id.
     * @return el número de filas que fueron modificadas en la tabla (1 si logout exitoso).
     */
    fun logOutUsuario(token: String) : Int {
        val usuarioDb = usuarioRepo.findByToken(token)
        if (usuarioDb != null) {
            usuarioDb.token = null
            usuarioRepo.save(usuarioDb)
            return 1
        }
        return 0
    }

    /**
     * Función para actualizar un usuario mediante su id.
     * Se verifica su token.
     *
     * @param token el token del usuario a modificar.
     * @param usuarioUpdateBody objeto con el id del usuario a actualizar.
     */
    fun updateUsuario(token: String, usuarioUpdateBody : UserUpdateBody) : User? {
        val usuarioObtenido = usuarioRepo.findByToken(token)?: throw IllegalArgumentException("Este usuario no existe")

        if(usuarioObtenido.token == null){
            throw IllegalArgumentException("La sesión de este usuario se encuentra cerrada.")
        } else if (usuarioObtenido.token != token){
            throw IllegalArgumentException("El Token dado no coincide con el token de el usuario.")
        }

        usuarioObtenido.nombre = usuarioUpdateBody.nombre ?: usuarioObtenido.nombre
        usuarioObtenido.apellidoP = usuarioUpdateBody.apellidoP ?: usuarioObtenido.apellidoP
        usuarioObtenido.apellidoM = usuarioUpdateBody.apellidoM ?: usuarioObtenido.apellidoM
        usuarioObtenido.mail = usuarioUpdateBody.mail ?: usuarioObtenido.mail
        usuarioObtenido.password = usuarioUpdateBody.password ?: usuarioObtenido.password
        usuarioObtenido.esAdministrador = usuarioObtenido.esAdministrador

        usuarioRepo.save(usuarioObtenido)

        val resultadoUsuarioAct = User (
            idUsuario = usuarioObtenido.idUsuario,
            nombre = usuarioObtenido.nombre,
            apellidoP = usuarioObtenido.apellidoP,
            apellidoM = usuarioObtenido.apellidoM,
            mail = usuarioObtenido.mail,
            password = usuarioObtenido.password,
            token = usuarioObtenido.token,
            esAdministrador = usuarioObtenido.esAdministrador
        )
        return  resultadoUsuarioAct
    }
}