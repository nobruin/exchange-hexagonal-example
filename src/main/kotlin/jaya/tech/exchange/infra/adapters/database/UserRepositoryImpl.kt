package jaya.tech.exchange.infra.adapters.database

import jaya.tech.exchange.application.domain.User
import jaya.tech.exchange.ports.output.persistence.entities.UserModel
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository

class UserRepositoryImpl: UserRepository {
    override fun createUser(user: User): UserModel{
        throw Exception("Not implemented yet")
    }
    override fun getUserByUsername(username: String): UserModel?{
        throw Exception("Not implemented yet")
    }
    override fun getById(userId: Long): UserModel{
        throw Exception("Not implemented yet")
    }
}