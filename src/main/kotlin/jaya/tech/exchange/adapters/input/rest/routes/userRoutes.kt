package jaya.tech.exchange.adapters.input.rest.routes

import io.javalin.Javalin
import io.javalin.http.bodyValidator
import jaya.tech.exchange.adapters.input.rest.dtos.CreateUserRequest


fun Javalin.userRoutes() {
    post("/users") { ctx ->

        val createUserRequest= ctx.bodyValidator<CreateUserRequest>()
            .check({ it.username.isNotBlank() }, "Username is required")
            .check({ it.email.isNotBlank() }, "Email is required")
            .check({ it.password.isNotBlank() }, "Password is required")
            .get()

        ctx.status(200)
        createUserRequest.let { ctx.json(it) }
    }
}
