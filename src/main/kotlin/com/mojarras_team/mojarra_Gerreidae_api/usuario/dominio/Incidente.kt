package com.mojarras_team.mojarra_Gerreidae_api.usuario.dominio

data class Incidente (
    var IDIncidente:  Int = -1,
    var IDUsuario: Int = -1,
    var Descripcion: String = "",
    var Tipo: String = "",
    var Estado: String = "",
    var Longitud: Double,
    var Latitud: Double,
    var Fecha: String = ""
)