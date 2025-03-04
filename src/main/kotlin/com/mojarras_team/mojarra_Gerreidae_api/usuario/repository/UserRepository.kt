package com.mojarras_team.mojarra_Gerreidae_api.repository

import com.mojarras_team.mojarra_Gerreidae_api.models.User
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

/**
 * Interfaz para realizr las consultas necesarias a la
 * tabla Usuario de la base de datos.
 */
interface UserRepository : CrudRepository<User, Int> {

    /**
     * Función para obtener un usuario por su correo y contraseña.
     *
     * @param mail correo del usuario.
     * @param contrasenia contraseña del usuario.
     * @param un User o null si no existe usuario asociado.
     */
    @Query(value = "SELECT * FROM usuario WHERE correo=?1 AND contrasenia=?2", nativeQuery = true)
    fun findByEmailAndPassword(mail: String, contrasenia: String): User?

    /**
     * Función para obtener un usuario por su correo.
     *
     * @param mail correo del uduario que quiere encontrar.
     * @return un User o null si no se encuentra.
     */
    @Query(value = "SELECT * FROM usuario WHERE correo=?1", nativeQuery = true)
    fun findByMail(mail: String): User?

    /**
     * Función para establecer el token del usuario con el id indicado
     * como null.
     *
     * @param idUsuario el ID del usuario al que se le quiere borrar el token.
     * @return número de filas afectadas.
     */
    @Modifying
    @Query(value = "UPDATE usuario SET token = null WHERE idUsuario = ?1", nativeQuery = true)
    fun deleteToken(idUsuario: Int): Int
}