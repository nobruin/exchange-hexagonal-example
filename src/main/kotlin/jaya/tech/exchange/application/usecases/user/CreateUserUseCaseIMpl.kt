package jaya.tech.exchange.application.usecases.user

import jaya.tech.exchange.application.domain.User
import jaya.tech.exchange.application.domain.toEntity
import jaya.tech.exchange.infra.adapters.Loggable
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository

class CreateUserUseCaseIMpl(private val userRepository: UserRepository): CreateUserUseCase, Loggable {

    override fun execute(username: String, email: String, password: String): User {
        return runCatching {
            User(
                email = email,
                username = username,
                password = password
            ).let {user ->
                userValidation(user)
                userRepository.createUser(user).toEntity().also {
                    log.info("User created with success")
                }
            }
        }.onFailure {
            throw it
        }.getOrThrow()
    }

    private fun userValidation(user: User): Boolean {
        takeUnless { user.isValid() }?.let {
            log.error("User is invalid")
            throw RuntimeException("User is invalid")
        }
        userRepository.getUserByUsername(user.username)?.let {
            log.error("User is invalid")
            throw RuntimeException("User already exists")
        }
        return true
    }


}