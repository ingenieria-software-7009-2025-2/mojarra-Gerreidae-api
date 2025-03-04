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
    val nombre: String,

    @Column(name = "apellidoP", nullable = false)
    val apellidoP: String,

    @Column(name = "apellidoM", nullable = false)
    val apellidoM: String,

    @Column(name = "correo", nullable = false, unique = true)
    val correo: String,

    @Column(name = "contrasenia", nullable = false)
    val contrasenia: String,

    @Column(name = "token")
    val token: String? = null
)