package jaya.tech.exchange.application.usecases.user

import jaya.tech.exchange.domain.User

interface CreateUserUseCase {
    fun execute(username: String,email: String, password: String ): User
}