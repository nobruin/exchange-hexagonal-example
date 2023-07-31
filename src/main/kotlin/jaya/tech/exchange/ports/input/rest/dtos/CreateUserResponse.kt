package jaya.tech.exchange.ports.input.rest.dtos

data class CreateUserResponse(
    val id: Long? = null,
    val username: String,
    val email: String
)