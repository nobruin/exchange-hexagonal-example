package jaya.tech.exchange.adapters.output.persistence.repositories

import jaya.tech.exchange.adapters.output.persistence.entities.UserModel

interface UserRepository {
    fun createUser(user: UserModel): UserModel
    fun getUserByUsername(username: String): UserModel?
    fun getById(userId: Long): UserModel
}