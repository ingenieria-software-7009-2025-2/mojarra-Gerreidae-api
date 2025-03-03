package com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies

//Objeto Body para actualizar los datos de la clase Usuario
data class UserUpdateBody(
    val idUsuario : String = "",
    val nombre : String = "",
    val apellidoP : String = "",
    val apellidoM : String = "",
    val mail : String = "",
    val password : String = "",
    val token : String = ""
)