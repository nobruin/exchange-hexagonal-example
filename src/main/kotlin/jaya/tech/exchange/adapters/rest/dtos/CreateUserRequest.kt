package jaya.tech.exchange.adapters.rest.dtos

import javax.validation.constraints.NotBlank

class CreateUserRequest(
    @field:NotBlank(message = "Username is required")
    val username: String,
    @field:NotBlank(message = "Email is required")
    val email: String,
    @field:NotBlank(message = "Password is required")
    val password: String
)
