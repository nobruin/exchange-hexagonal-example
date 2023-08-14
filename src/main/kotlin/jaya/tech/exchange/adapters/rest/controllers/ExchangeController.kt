package jaya.tech.exchange.adapters.rest.controllers

import jaya.tech.exchange.adapters.rest.dtos.ExchangeRequest
import jaya.tech.exchange.adapters.rest.dtos.ExchangeResponse
import jaya.tech.exchange.application.exceptions.UnauthorizedException
import jaya.tech.exchange.ports.input.usecases.exchange.ConvertCurrencyUseCase
import jaya.tech.exchange.ports.input.authentication.JwtTokenProvider

class ExchangeController(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun exchange(token: String, request: ExchangeRequest): ExchangeResponse? =
        runCatching {
            jwtTokenProvider.getUserFromToken(token)?.let { authUserDTO ->
                convertCurrencyUseCase.execute(
                    userId = authUserDTO.id,
                    amount = request.amount,
                    fromCurrency = request.fromCurrency,
                    toCurrency = request.toCurrency,
                )
            }
        }.onFailure {
            throw UnauthorizedException(INVALID_TOKEN)
        }.getOrThrow()

    companion object {
        const val INVALID_TOKEN = "Invalid Token"
    }
}
