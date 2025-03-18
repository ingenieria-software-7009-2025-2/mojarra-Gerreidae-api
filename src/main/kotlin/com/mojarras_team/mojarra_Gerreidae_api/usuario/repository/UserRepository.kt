package com.mojarras_team.mojarra_Gerreidae_api.usuario.repository

import com.mojarras_team.mojarra_Gerreidae_api.usuario.repository.entity.UserEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Interfaz para realizr las consultas necesarias a la
 * tabla Usuario de la base de datos.
 */
interface UserRepository : CrudRepository<UserEntity, Int> {

    /**
     * Función para obtener un usuario por su mail y password.
     *
     * @param mail mail del usuario.
     * @param password password del usuario.
     * @return un User o null si no existe usuario asociado.
     */
    @Query(value = "SELECT * FROM mojarra_esquema.usuario WHERE mail=?1 AND password=?2", nativeQuery = true)
    fun findByEmailAndPassword(mail: String, password: String): UserEntity?

    /**
     * Función para obtener un usuario por su mail.
     *
     * @param mail mail del usuario que se quiere encontrar.
     * @return un User o null si no se encuentra.
     */
    @Query(value = "SELECT * FROM mojarra_esquema.usuario WHERE mail=?1", nativeQuery = true)
    fun findByMail(mail: String): UserEntity?

    /**
     * Función para obtener un usuario por su token.
     *
     * @param token del usuario que se quiere encontrar.
     * @return un User o null si no se encuentra.
     */
    @Query(value = "SELECT * FROM mojarra_esquema.usuario WHERE token=?1", nativeQuery = true)
    fun findByToken(token: String): UserEntity?
}