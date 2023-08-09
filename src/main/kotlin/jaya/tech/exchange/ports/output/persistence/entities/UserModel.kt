package jaya.tech.exchange.ports.output.persistence.entities

import jaya.tech.exchange.adapters.rest.dtos.AuthUserDTO
import java.util.UUID

data class UserModel(
    val id: UUID? = null,
    val username: String,
    val email: String,
    val password: String
)

fun UserModel.toAuthUserDTO() = AuthUserDTO(id = id!!, username = username, email = email)
