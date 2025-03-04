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
        usuario.token = UUID.randomUUID().toString()
        usuarioRepo.save(usuario)
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
        val usuarioDb = usuarioRepo.findById(usuarioLogOutBody.idUsuario).get();
        usuarioDb.token = null;
        usuarioRepo.save(usuarioDb);
        //val usuarioObtenido = usuarioRepo.deleteToken(usuarioLogOutBody.idUsuario)
        //return usuarioObtenido
        return 1
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

        usuarioObtenido.nombre = usuarioUpdateBody.nombre ?: usuarioObtenido.nombre
        usuarioObtenido.apellidoP = usuarioUpdateBody.apellidoP ?: usuarioObtenido.apellidoP
        usuarioObtenido.apellidoM = usuarioUpdateBody.apellidoM ?: usuarioObtenido.apellidoM
        usuarioObtenido.correo = usuarioUpdateBody.mail ?: usuarioObtenido.correo
        usuarioObtenido.contrasenia = usuarioUpdateBody.password ?: usuarioObtenido.contrasenia

        usuarioRepo.save(usuarioObtenido)

        val resultadoUsuarioAct = User (
            IDUsuario = usuarioObtenido.idUsuario,
            Nombre = usuarioObtenido.nombre,
            ApellidoP = usuarioObtenido.apellidoP,
            ApellidoM = usuarioObtenido.apellidoM,
            Correo = usuarioObtenido.correo,
            Contrasenia = usuarioObtenido.contrasenia,
            Token = usuarioObtenido.token
        )
        return  resultadoUsuarioAct
    }
}