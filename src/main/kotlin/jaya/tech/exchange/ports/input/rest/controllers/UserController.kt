package jaya.tech.exchange.ports.input.rest.controllers

import jaya.tech.exchange.application.usecases.user.CreateUserUseCase
import jaya.tech.exchange.application.usecases.user.LoginUseCase
import jaya.tech.exchange.ports.input.rest.dtos.CreateUserRequest
import jaya.tech.exchange.ports.input.rest.dtos.CreateUserResponse
import jaya.tech.exchange.ports.input.rest.dtos.LoginRequest
import jaya.tech.exchange.ports.output.authentication.JwtTokenProvider

class UserController(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun createUser(request: CreateUserRequest): CreateUserResponse {
        val user = createUserUseCase.execute(request.username, request.email, request.password)
        return CreateUserResponse(
            user.id,
            user.username,
            user.email
        )
    }

    fun login(request: LoginRequest) = runCatching {
        val user = loginUseCase.execute(request.username, request.password)
        jwtTokenProvider.createToken(user)
    }.onFailure {
        throw Exception(INVALID_CREDENTIALS_MESSAGE)
    }.getOrThrow()

    companion object {
        const val INVALID_CREDENTIALS_MESSAGE = "Your Credentials are invalid!"
    }
}
