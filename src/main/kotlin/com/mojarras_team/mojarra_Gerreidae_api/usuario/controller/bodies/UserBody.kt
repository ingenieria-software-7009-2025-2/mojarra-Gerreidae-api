package com.mojarras_team.mojarra_Gerreidae_api.bodies

//Objeto Body para manejar los datos del usuario
data class UserBody(
    val idUsuario : Int = -1,
    val nombre : String = "",
    val apellidoP : String = "",
    val apellidoM : String = "",
    val mail : String = "",
    val password : String = "",
    val token : String = ""
)