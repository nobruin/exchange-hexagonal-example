package jaya.tech.exchange.adapters.input.rest.controllers

import jaya.tech.exchange.adapters.input.rest.dtos.CreateUserRequest
import jaya.tech.exchange.adapters.input.rest.dtos.CreateUserResponse
import jaya.tech.exchange.application.usecases.user.CreateUserUseCase

class UserController(private val createUserUseCase: CreateUserUseCase) {
    fun createUser(request: CreateUserRequest): CreateUserResponse {
        val user = createUserUseCase.execute(request.username, request.email, request.password)
        return CreateUserResponse(
            user.id,
            user.username,
            user.email
        )
    }
}