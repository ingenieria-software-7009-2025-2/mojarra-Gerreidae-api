package com.mojarras_team.mojarra_Gerreidae_api.usuario.dominio

data class Incidente (
    var idIncidente:  Int = -1,
    var idUsuario: Int = -1,
    var descripcion: String = "",
    var tipo: String = "",
    var estado: String = "",
    var longitud: Double,
    var latitud: Double,
    var fecha: String = ""
)