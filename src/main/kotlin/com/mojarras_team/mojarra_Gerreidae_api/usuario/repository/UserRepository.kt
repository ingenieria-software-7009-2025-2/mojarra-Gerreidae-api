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
     * Función para obtener un usuario por su correo y contraseña.
     *
     * @param mail correo del usuario.
     * @param contrasenia contraseña del usuario.
     * @return un User o null si no existe usuario asociado.
     */
    @Query(value = "SELECT * FROM mojarra_esquema.usuario WHERE correo=?1 AND contrasenia=?2", nativeQuery = true)
    fun findByEmailAndPassword(mail: String, contrasenia: String): UserEntity?

    /**
     * Función para obtener un usuario por su correo.
     *
     * @param mail correo del usuario que quiere encontrar.
     * @return un User o null si no se encuentra.
     */
    @Query(value = "SELECT * FROM mojarra_esquema.usuario WHERE correo=?1", nativeQuery = true)
    fun findByMail(mail: String): UserEntity?
}