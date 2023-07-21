package jaya.tech.exchange.adapters.input.rest.controllers

import jaya.tech.exchange.adapters.input.rest.dtos.ExchangeRequest
import jaya.tech.exchange.adapters.input.rest.dtos.ExchangeResponse
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCase

class ExchangeController(private val convertCurrencyUseCase: ConvertCurrencyUseCase) {
    fun exchange(request: ExchangeRequest): ExchangeResponse {
        //TODO: jwtService
        val result = convertCurrencyUseCase.execute(request.amount, request.fromCurrency, request.toCurrency)
        return ExchangeResponse(result)
    }
}