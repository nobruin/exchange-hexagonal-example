package jaya.tech.exchange.ports.output.authentication

import jaya.tech.exchange.ports.input.rest.dtos.AuthUserDTO

interface JwtTokenProvider {
    fun createToken(user: AuthUserDTO): String
    fun validateToken(token: String): Boolean
    fun getUserFromToken(token: String): AuthUserDTO?
}
