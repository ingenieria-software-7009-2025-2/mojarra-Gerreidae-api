package com.mojarras_team.mojarra_Gerreidae_api.usuario.dominio

data class User(
    var idUsuario:  Int = -1,
    var nombre: String,
    var apellidoP: String,
    var apellidoM: String,
    var mail: String,
    var password: String,
    var token:String? = null
)