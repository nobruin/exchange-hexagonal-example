package jaya.tech.exchange.adapters.rest.dtos

import java.util.UUID

data class CreateUserResponse(
    val id: UUID? = null,
    val username: String,
    val email: String
)
