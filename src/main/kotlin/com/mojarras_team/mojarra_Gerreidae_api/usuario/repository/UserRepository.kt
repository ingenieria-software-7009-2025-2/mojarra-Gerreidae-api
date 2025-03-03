package com.mojarras_team.mojarra_Gerreidae_api.repository

import com.mojarras_team.mojarra_Gerreidae_api.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {
}