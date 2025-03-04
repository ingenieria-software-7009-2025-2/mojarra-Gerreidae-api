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

    @Column(name = "nombre", nullable = false, length = 50)
    val nombre: String,

    @Column(name = "apellidoP", nullable = false, length = 50)
    val apellidoP: String,

    @Column(name = "apellidoM", nullable = false, length = 50)
    val apellidoM: String,

    @Column(name = "correo", nullable = false, length = 30, unique = true)
    val correo: String,

    @Column(name = "contrasenia", nullable = false, length = 30)
    val contrasenia: String,

    @Column(name = "token", length = 30)
    val token: String? = null
)