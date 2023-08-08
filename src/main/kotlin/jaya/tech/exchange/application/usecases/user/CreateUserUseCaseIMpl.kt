package jaya.tech.exchange.application.usecases.user

import jaya.tech.exchange.application.domain.User
import jaya.tech.exchange.application.domain.toEntity
import jaya.tech.exchange.infra.adapters.Loggable
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository

class CreateUserUseCaseIMpl(private val userRepository: UserRepository) : CreateUserUseCase, Loggable {

    override fun execute(username: String, email: String, password: String): User {
        return runCatching {
            User(
                email = email,
                username = username,
                password = password
            ).let { user ->
                userValidation(user)
                userRepository.save(user).toEntity().also {
                    log.info(USER_CREATED_MESSAGE)
                }
            }
        }.onFailure { ex ->
            throw ex
        }.getOrThrow()
    }

    private fun userValidation(user: User): Boolean {
        takeUnless { user.isValid() }?.let {
            log.error(USER_INVALID_MESSAGE)
            throw RuntimeException(USER_INVALID_MESSAGE)
        }
        userRepository.getUserByUsername(user.username)?.let {
            log.error(USER_ALREADY_EXISTS_MESSAGE)
            throw RuntimeException(USER_ALREADY_EXISTS_MESSAGE)
        }
        return true
    }

    companion object {
        const val USER_INVALID_MESSAGE = "User is invalid"
        const val USER_ALREADY_EXISTS_MESSAGE = "User already exists"
        const val USER_CREATED_MESSAGE = "User created with success"
    }
}
