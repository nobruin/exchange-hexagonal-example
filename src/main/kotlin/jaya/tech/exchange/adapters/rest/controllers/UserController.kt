package jaya.tech.exchange.adapters.rest.controllers

import jaya.tech.exchange.adapters.rest.dtos.CreateUserRequest
import jaya.tech.exchange.adapters.rest.dtos.CreateUserResponse
import jaya.tech.exchange.adapters.rest.dtos.LoginRequest
import jaya.tech.exchange.application.exceptions.UnauthorizedException
import jaya.tech.exchange.ports.input.authentication.JwtTokenProvider
import jaya.tech.exchange.ports.input.usecases.user.CreateUserUseCase
import jaya.tech.exchange.ports.input.usecases.user.LoginUseCase

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
        throw UnauthorizedException(INVALID_CREDENTIALS_MESSAGE)
    }.getOrThrow()

    companion object {
        const val INVALID_CREDENTIALS_MESSAGE = "Your Credentials are invalid!"
    }
}
