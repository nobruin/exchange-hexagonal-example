package jaya.tech.exchange.adapters.rest.routes

import io.javalin.Javalin
import io.javalin.http.bodyValidator
import io.javalin.validation.ValidationError
import jaya.tech.exchange.adapters.rest.controllers.UserController
import jaya.tech.exchange.adapters.rest.dtos.CreateUserRequest
import jaya.tech.exchange.adapters.rest.dtos.LoginRequest
import jaya.tech.exchange.application.exceptions.BadRequestException
import org.koin.java.KoinJavaComponent

fun buildErrorMessage(violations: Map<String, List<ValidationError<Any>>>): String {
    return violations.values.flatten().joinToString(", ") { it.message }
}
fun Javalin.userRoutes(version: String) {
    val userController: UserController by KoinJavaComponent.inject(UserController::class.java)
    post("$version/users") { ctx ->
        val createUserRequest = ctx.bodyValidator<CreateUserRequest>()
            .check({ it.username.isNotBlank() }, "Username is required")
            .check({ it.email.isNotBlank() }, "Email is required")
            .check({ it.password.isNotBlank() }, "Password is required")
            .getOrThrow {
                BadRequestException(buildErrorMessage(it))
            }

        userController.createUser(createUserRequest).let {
            ctx.status(200)
            ctx.json(it)
        }
    }

    post("$version/auth/login") { ctx ->
        val loginRequest = ctx.bodyValidator<LoginRequest>()
            .check({ it.username.isNotBlank() }, "Username is required")
            .check({ it.password.isNotBlank() }, "Password is required")
            .getOrThrow {
                BadRequestException(buildErrorMessage(it))
            }

        userController.login(loginRequest).let {
            ctx.status(200)
            ctx.json(it)
        }
    }
}
