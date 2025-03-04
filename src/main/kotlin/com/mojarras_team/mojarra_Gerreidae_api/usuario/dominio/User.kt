package com.mojarras_team.mojarra_Gerreidae_api.usuario.dominio

data class User(
    var IDUsuario:  Int = -1,
    var Nombre: String,
    var ApellidoP: String,
    var ApellidoM: String,
    var Correo: String,
    var Contrasenia: String,
    var Token:String? = null
)