package jaya.tech.exchange.application.usecases.user

import jaya.tech.exchange.adapters.input.rest.dtos.AuthUserDTO

interface LoginUseCase {
    fun execute(userName: String, password: String): AuthUserDTO
}