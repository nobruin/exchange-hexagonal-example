package jaya.tech.exchange.adapters.output.authentication

import jaya.tech.exchange.adapters.input.rest.dtos.AuthUserDTO

interface JwtTokenProvider {
    fun createToken(user: AuthUserDTO): String
    fun validateToken(token: String): Boolean
    fun getUserFromToken(token: String): AuthUserDTO?
}