package jaya.tech.exchange.domain

import jaya.tech.exchange.adapters.output.persistence.entities.UserModel

data class User(val id: Long? = null, val username: String, val email: String, val password: String) {
    fun isValid() = true
}

fun UserModel.toEntity() = User(
    id = id,
    username = username,
    email = email,
    password = password
)