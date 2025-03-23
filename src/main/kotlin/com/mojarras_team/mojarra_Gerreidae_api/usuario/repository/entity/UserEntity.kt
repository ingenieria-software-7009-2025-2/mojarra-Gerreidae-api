package com.mojarras_team.mojarra_Gerreidae_api.usuario.repository.entity

import jakarta.persistence.*

/**
 * Clase para modelar la tabla Usuario de la base de datos.
 */
@Entity
@Table(name = "usuario", schema = "mojarra_esquema")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    val idUsuario: Int,

    @Column(name = "nombre", nullable = false)
    var nombre: String,

    @Column(name = "apellido_p", nullable = false)
    var apellidoP: String,

    @Column(name = "apellido_m", nullable = false)
    var apellidoM: String,

    @Column(name = "mail", nullable = false, unique = true)
    var mail: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "token")
    var token: String? = null
)