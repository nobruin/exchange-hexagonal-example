package jaya.tech.exchange.ports.input.authentication

import jaya.tech.exchange.adapters.rest.dtos.AuthUserDTO

interface JwtTokenProvider {
    fun createToken(user: AuthUserDTO): String
    fun validateToken(token: String): Boolean
    fun getUserFromToken(token: String): AuthUserDTO?
}
