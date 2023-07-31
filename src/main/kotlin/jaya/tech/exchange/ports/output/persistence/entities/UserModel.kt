package jaya.tech.exchange.ports.output.persistence.entities

data class UserModel(
    val id: Long? = null,
    val username: String,
    val email: String,
    val password: String
)