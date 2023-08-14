package jaya.tech.exchange.application.usecases.user

import jaya.tech.exchange.adapters.rest.dtos.AuthUserDTO
import jaya.tech.exchange.infra.Loggable
import jaya.tech.exchange.ports.input.usecases.user.LoginUseCase
import jaya.tech.exchange.ports.output.persistence.entities.toAuthUserDTO
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository

class LoginUseCaseImpl(private val userRepository: UserRepository) : LoginUseCase, Loggable {
    override fun execute(userName: String, password: String): AuthUserDTO =
        userRepository.getUserByUsernameAndPassword(userName, password)?.let {
            it.toAuthUserDTO().also { _ ->
                log.info("User ${it.username} logged in")
            }
        } ?: throw Exception(INVALID_CREDENTIALS_MESSAGE).also { ex ->
            log.error(INVALID_CREDENTIALS_MESSAGE, ex)
        }

    companion object {
        const val INVALID_CREDENTIALS_MESSAGE = "Your Credentials are invalid!"
    }
}
