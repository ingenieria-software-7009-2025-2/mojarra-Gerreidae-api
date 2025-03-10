package com.mojarras_team.mojarra_Gerreidae_api.usuario.dominio

class  Actualizacion (
    var idActualizacion:  Int = -1,
    var idUsuario: Int,
    var idIncidente: Int,
    var nuevoEstado: String = "",
    var fecha: String = "",
    var descripcion: String = ""
)