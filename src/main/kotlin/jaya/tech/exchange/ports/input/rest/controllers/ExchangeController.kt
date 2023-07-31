package jaya.tech.exchange.ports.input.rest.controllers

import jaya.tech.exchange.ports.input.rest.dtos.ExchangeRequest
import jaya.tech.exchange.ports.input.rest.dtos.ExchangeResponse
import jaya.tech.exchange.ports.output.authentication.JwtTokenProvider
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCase

class ExchangeController(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun exchange(token: String, request: ExchangeRequest): ExchangeResponse?
       = runCatching {
           jwtTokenProvider.getUserFromToken(token)?.let { authUserDTO ->
               convertCurrencyUseCase.execute(
                   userId = authUserDTO.id,
                   amount = request.amount,
                   fromCurrency = request.fromCurrency,
                   toCurrency = request.toCurrency,
               )
           }
       }.onFailure {
           throw Exception(INVALID_TOKEN)
       }.getOrThrow()


    companion object{
        const val INVALID_TOKEN = "Invalid Token"
    }
}