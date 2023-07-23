package jaya.tech.exchange.adapters.output.persistence.entities

data class UserModel(
    val id: Long? = null,
    val username: String,
    val email: String,
    val password: String
)