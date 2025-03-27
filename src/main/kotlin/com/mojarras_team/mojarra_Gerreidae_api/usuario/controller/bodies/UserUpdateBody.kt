package com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies

//Objeto Body para actualizar los datos de la clase Usuario
data class UserUpdateBody(
    val idUsuario : Int = -1,
    val nombre : String? ,
    val apellidoP : String? ,
    val apellidoM : String? ,
    val mail : String? ,
    val password : String?
)