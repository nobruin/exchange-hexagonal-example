package jaya.tech.exchange.adapters.rest.routes

import io.javalin.Javalin
import io.javalin.http.bodyValidator
import jaya.tech.exchange.adapters.rest.controllers.ExchangeController
import jaya.tech.exchange.adapters.rest.dtos.ExchangeRequest
import jaya.tech.exchange.application.exceptions.BadRequestException
import org.koin.java.KoinJavaComponent

fun Javalin.exchangeRoutes(version: String) {
    val exchangeController: ExchangeController by KoinJavaComponent.inject(ExchangeController::class.java)

    post("$version/exchange") { ctx ->
        val token = ctx.header("Authorization") ?: throw Exception("Token is required")
        val exchangeRequest: ExchangeRequest = ctx.bodyValidator<ExchangeRequest>()
            .check({ it.fromCurrency.isNotBlank() }, "from currency is required")
            .check({ it.toCurrency.isNotBlank() }, "to currency is required")
            .check({ it.amount > 0.toBigDecimal() }, "Amount must be greater than 0")
            .getOrThrow {
                BadRequestException(buildErrorMessage(it))
            }

        exchangeController.exchange(token, exchangeRequest).let {
            ctx.status(200)
            ctx.json(it!!)
        }
    }
}
