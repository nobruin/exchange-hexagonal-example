package jaya.tech.exchange.ports.input.rest.routes

import io.javalin.Javalin
import io.javalin.http.bodyValidator
import jaya.tech.exchange.ports.input.rest.controllers.UserController
import jaya.tech.exchange.ports.input.rest.dtos.CreateUserRequest

import org.koin.java.KoinJavaComponent


fun Javalin.userRoutes(version: String) {
    val userController: UserController by KoinJavaComponent.inject(UserController::class.java)
    post("$version/users") { ctx ->
        val createUserRequest= ctx.bodyValidator<CreateUserRequest>()
            .check({ it.username.isNotBlank() }, "Username is required")
            .check({ it.email.isNotBlank() }, "Email is required")
            .check({ it.password.isNotBlank() }, "Password is required")
            .get()

        userController.createUser(createUserRequest).let {
            ctx.status(200)
            ctx.json(it)
        }
    }
}
