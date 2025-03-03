package com.mojarras_team.mojarra_Gerreidae_api.usuario.dominio

data class Incidente (
    var id:  Int = -1,
    var idUsuario: Int = -1,
    var descripcion: String = "",
    var tipo: String = "",
    var estado: String = "",
    var longitud: Double? = null,
    var latitud: Double? = null,
    var fecha: String = ""
)