package jaya.tech.exchange.application.usecases.user

import jaya.tech.exchange.domain.User

interface LoginUseCase {
    fun execute(userName: String, password: String): User
}