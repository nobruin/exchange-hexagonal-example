package jaya.tech.exchange.application.usecases.user

import jaya.tech.exchange.ports.input.rest.dtos.AuthUserDTO

class LoginUseCaseImpl(): LoginUseCase {
    override fun execute(userName: String, password: String): AuthUserDTO{
        throw Exception("Not implemented yet")
    }
}