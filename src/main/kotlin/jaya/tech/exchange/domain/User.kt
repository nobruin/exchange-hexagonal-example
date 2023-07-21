package jaya.tech.exchange.domain

data class User(val id: Long? = null, val username: String, val email: String, val password: String) {
    fun isValid() = true
}