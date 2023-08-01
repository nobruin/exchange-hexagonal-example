package jaya.tech.exchange.application.domain

import jaya.tech.exchange.ports.output.persistence.entities.UserModel

data class User(val id: Long? = null, val username: String, val email: String, val password: String) {
    fun isValid(): Boolean = username.isNotBlank() && email.isNotBlank() && password.isNotBlank()
}

fun UserModel.toEntity() = User(
    id = id,
    username = username,
    email = email,
    password = password
)