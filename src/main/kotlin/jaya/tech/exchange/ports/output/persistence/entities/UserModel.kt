package jaya.tech.exchange.ports.output.persistence.entities

import java.util.UUID

data class UserModel(
    val id: UUID? = null,
    val username: String,
    val email: String,
    val password: String
)