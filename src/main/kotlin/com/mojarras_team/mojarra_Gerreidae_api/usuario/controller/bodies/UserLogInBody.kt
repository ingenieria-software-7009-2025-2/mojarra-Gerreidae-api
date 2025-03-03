package com.mojarras_team.mojarra_Gerreidae_api.usuario.controller.bodies

// Objeto body que maneja los datos del Usuario para hacer inicio de sesión
data class UserLogInBody(
    val mail : String = "",
    val password : String = ""
)