package jaya.tech.exchange.adapters.rest.dtos

import jaya.tech.exchange.ports.output.persistence.entities.UserModel
import java.util.UUID

class AuthUserDTO(val id: UUID, val username: String, val email: String)

fun UserModel.toAuthUserDTO() = AuthUserDTO(id = id!!, username = username, email = email)
