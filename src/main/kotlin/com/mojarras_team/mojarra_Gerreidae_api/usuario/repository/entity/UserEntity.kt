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
    @Column(name = "idUsuario")
    val idUsuario: Int,

    @Column(name = "nombre", nullable = false)
    var nombre: String,

    @Column(name = "apellidoP", nullable = false)
    var apellidoP: String,

    @Column(name = "apellidoM", nullable = false)
    var apellidoM: String,

    @Column(name = "correo", nullable = false, unique = true)
    var correo: String,

    @Column(name = "contrasenia", nullable = false)
    var contrasenia: String,

    @Column(name = "token")
    var token: String? = null
)