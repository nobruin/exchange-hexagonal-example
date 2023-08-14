package jaya.tech.exchange.infra.authentication

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jaya.tech.exchange.adapters.rest.dtos.AuthUserDTO
import jaya.tech.exchange.ports.input.authentication.JwtTokenProvider
import java.security.SecureRandom
import java.util.Date
import java.util.UUID

class JwtTokenProviderImpl : JwtTokenProvider {
    private val expirationTimeMs: Long = 86400000
    private val key = Keys.hmacShaKeyFor(SecureRandom.getInstanceStrong().generateSeed(32))

    override fun createToken(user: AuthUserDTO): String {
        return Jwts.builder()
            .claim("id", user.id)
            .claim("username", user.username)
            .claim("email", user.email)
            .setExpiration(Date(System.currentTimeMillis() + expirationTimeMs))
            .signWith(key)
            .compact()
    }

    override fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getUserFromToken(token: String): AuthUserDTO? {
        return try {
            val claims: Claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
            val id = claims["id"] as UUID
            val username = claims["username"] as String
            val email = claims["email"] as String
            AuthUserDTO(id, username, email)
        } catch (e: Exception) {
            null
        }
    }
}
