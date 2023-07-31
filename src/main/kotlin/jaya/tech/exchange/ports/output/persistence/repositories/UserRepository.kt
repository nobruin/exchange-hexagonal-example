package jaya.tech.exchange.ports.output.persistence.repositories

import jaya.tech.exchange.ports.output.persistence.entities.UserModel

interface UserRepository {
    fun createUser(user: UserModel): UserModel
    fun getUserByUsername(username: String): UserModel?
    fun getById(userId: Long): UserModel
}