package jaya.tech.exchange.ports.input.usecases.user

import jaya.tech.exchange.adapters.rest.dtos.AuthUserDTO

interface LoginUseCase {
    fun execute(userName: String, password: String): AuthUserDTO
}
