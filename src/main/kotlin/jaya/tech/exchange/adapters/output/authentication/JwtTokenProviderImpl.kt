package jaya.tech.exchange.adapters.output.authentication

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jaya.tech.exchange.adapters.input.rest.dtos.AuthUserDTO
import java.util.Date

class JwtTokenProviderImpl(private val secretKey: String, private val expirationTimeMs: Long) : JwtTokenProvider{

    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())
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
            val id = claims["id"] as Long
            val username = claims["username"] as String
            val email = claims["email"] as String
            AuthUserDTO(id, username, email)
        } catch (e: Exception) {
            null
        }
    }
}

