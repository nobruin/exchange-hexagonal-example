package jaya.tech.exchange.adapters.output.authentication

import jaya.tech.exchange.adapters.input.rest.dtos.AuthUserDTO
import jaya.tech.exchange.domain.User

interface JwtTokenProvider {
    fun createToken(user: User): String
    fun validateToken(token: String): Boolean
    fun getUserFromToken(token: String): AuthUserDTO?
}