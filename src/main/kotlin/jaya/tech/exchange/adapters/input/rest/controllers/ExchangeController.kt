package jaya.tech.exchange.adapters.input.rest.controllers

import jaya.tech.exchange.adapters.input.rest.dtos.ExchangeRequest
import jaya.tech.exchange.adapters.input.rest.dtos.ExchangeResponse
import jaya.tech.exchange.application.usecases.ExchangeUseCase

class ExchangeController {

    class ExchangeController(private val exchangeUseCase: ExchangeUseCase) {
        fun exchange(request: ExchangeRequest): ExchangeResponse {
            val result = exchangeUseCase.convertCurrency(request.amount, request.fromCurrency, request.toCurrency)
            return ExchangeResponse(result)
        }
    }
}