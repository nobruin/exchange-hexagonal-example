package jaya.tech.exchange.adapters.input.rest.dtos

data class CreateUserResponse(
    val id: Long? = null,
    val username: String,
    val email: String
)