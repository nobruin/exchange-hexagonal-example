package jaya.tech.exchange.application.usecases.user

import jaya.tech.exchange.ports.output.persistence.entities.UserModel
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository
import jaya.tech.exchange.application.domain.User
import jaya.tech.exchange.application.domain.toEntity

class CreateUserUseCaseIMpl(private val userRepository: UserRepository): CreateUserUseCase{

    override fun execute(username: String, email: String, password: String): User {
        return runCatching {
            UserModel(
                email = email,
                username = username,
                password = password
            ).let {
                userRepository.createUser(it).toEntity()
            }
        }.onFailure {
            throw it
        }.getOrThrow()
    }
}