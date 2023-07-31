package jaya.tech.exchange.infra.database

import jaya.tech.exchange.adapters.output.persistence.entities.UserModel
import jaya.tech.exchange.adapters.output.persistence.repositories.UserRepository

class UserRepositoryImpl: UserRepository {
    override fun createUser(user: UserModel): UserModel{
        throw Exception("Not implemented yet")
    }
    override fun getUserByUsername(username: String): UserModel?{
        throw Exception("Not implemented yet")
    }
    override fun getById(userId: Long): UserModel{
        throw Exception("Not implemented yet")
    }
}