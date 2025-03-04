package com.mojarras_team.mojarra_Gerreidae_api.usuario.service

import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserBody
import com.mojarras_team.mojarra_Gerreidae_api.models.User
import com.mojarras_team.mojarra_Gerreidae_api.repository.UserRepository
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserLogInBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserLogoutBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserMeBody
import com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies.UserUpdateBody
import org.springframework.stereotype.Service
import java.util.*
import kotlin.reflect.full.memberProperties

@Service
class UsuarioService (private var usuarioRepo : UserRepository) {

    fun crearUsuario(usuarioBody : UserBody) : User {
        val usuarioDB = User(
            IDUsuario = usuarioBody.idUsuario,
            Nombre = usuarioBody.nombre,
            ApellidoP = usuarioBody.apellidoP,
            ApellidoM = usuarioBody.apellidoM,
            Correo = usuarioBody.mail,
            Contrasenia = usuarioBody.password,
            Token = UUID.randomUUID().toString()
        )
        val result = usuarioRepo.save(usuarioDB)
        return usuarioDB
    }

    fun obtenerUsuario(usuarioMeBody : UserMeBody) : User? {
        val usuarioObtenido = usuarioRepo.findById(usuarioMeBody.idUsuario.toInt())
        if (usuarioObtenido.isEmpty){
            throw IllegalArgumentException("Este usuario no existe.")
        }
        if (usuarioObtenido.get().Token == usuarioMeBody.token){
            return usuarioObtenido.get()
        }
        return null

    }

    fun logInUsuario(usuarioLogInBody : UserLogInBody) : User? {
        val usuarioObtenido = usuarioRepo.findByMail(usuarioLogInBody.mail)
            ?: throw IllegalArgumentException("Este usuario no existe")
        if (usuarioObtenido.Contrasenia == usuarioLogInBody.password){
            return usuarioObtenido
        }
        return null
    }

    fun logOutUsuario(usuarioLogOutBody : UserLogoutBody) : Int {
        val usuarioObtenido = usuarioRepo.deleteToken(usuarioLogOutBody.idUsuario)
        return usuarioObtenido
    }

    fun updateUsuario(usuarioUpdateBody : UserUpdateBody) : User? {
        val usuarioObtenido = usuarioRepo.findById(usuarioUpdateBody.idUsuario.toInt())

        if (usuarioObtenido.isEmpty){
            throw IllegalArgumentException("Este usuario no existe")
        }
        if(usuarioObtenido.get().Token == null){
            return null
        }
        val valores = mutableMapOf<String, String>()
        for (prop in User::class.memberProperties){
            valores[prop.name] = prop.get(usuarioObtenido.get()).toString() ?: ""
        }
        for (prop in UserUpdateBody::class.memberProperties){
            val nombreCampo = prop.name
            val valorCampo = prop.get(usuarioUpdateBody).toString() ?: ""
            if (valorCampo == ""){
                valores[nombreCampo] = valorCampo
            }

        }

        val usuarioActualizado = User(
            IDUsuario = valores["IDUsuario"]?.toInt() ?: 0,
            Nombre = valores["Nombre"] ?: "No alcanzable",
            ApellidoP = valores["ApellidoP"] ?: "No alcanzable",
            ApellidoM = valores["ApellidoM"] ?: "No alcanzable",
            Correo = valores["ApellidoM"] ?: "No alcanzable",
            Contrasenia = valores["Contrasenia"] ?: "No alcanzable",
            Token = valores["Token"] ?: "No alcanzable"
        )

        val resultado = usuarioRepo.save(usuarioActualizado)
        return  resultado
    }
}