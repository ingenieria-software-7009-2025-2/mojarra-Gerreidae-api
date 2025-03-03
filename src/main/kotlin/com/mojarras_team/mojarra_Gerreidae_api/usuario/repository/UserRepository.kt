package com.mojarras_team.mojarra_Gerreidae_api.repository

import com.mojarras_team.mojarra_Gerreidae_api.models.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {

    @Query(value = "SELECT * FROM usuario WHERE correo=?1 AND contrasenia=?2", nativeQuery = true)
    fun findByEmailAndPassword(email: String, password: String): User?

    @Query(value = "SELECT * FROM usuario WHERE correo=?1", nativeQuery = true)
    fun findByMail(mail: String): User?

    @Query(value = "UPDATE usuario SET token = null WHERE idUsuario = ?1", nativeQuery = true)
    fun deleteToken(idUsuario: Int): User?
}