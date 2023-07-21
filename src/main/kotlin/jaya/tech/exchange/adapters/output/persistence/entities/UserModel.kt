package jaya.tech.exchange.adapters.output.persistence.entities

import jaya.tech.exchange.domain.User

data class UserModel(
    val id: Long? = null,
    val username: String,
    val email: String,
    val password: String
){
    fun toEntity() = User(
        id = id,
        username = username,
        email = email,
        password = password
    )
}