package com.mojarras_team.mojarra_Gerreidae_api.models

data class User(
    var id: Int = -1,
    var nombre: String,
    var mail: String,
    var password: String? = null,
    var token:String? = null,
)