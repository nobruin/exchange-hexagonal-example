package jaya.tech.exchange.adapters.rest

import io.javalin.http.Context
import jaya.tech.exchange.application.exceptions.BadRequestException
import jaya.tech.exchange.application.exceptions.UnauthorizedException

fun handleException(e: Exception, ctx: Context) {
    val errorResponse = mapOf(
        "status" to "error",
        "message" to e.message
    )

    val status = when (e) {
        is BadRequestException -> 400
        is UnauthorizedException -> 401
        else -> 500
    }

    ctx.json(errorResponse).status(status)
}
