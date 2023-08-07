package jaya.tech.exchange.ports.output.persistence.repositories

import jaya.tech.exchange.application.domain.User
import jaya.tech.exchange.ports.output.persistence.entities.UserModel
import java.util.UUID

interface UserRepository {
    fun save(user: User): UserModel
    fun getUserByUsername(username: String): UserModel?
    fun getById(userId: UUID): UserModel
}