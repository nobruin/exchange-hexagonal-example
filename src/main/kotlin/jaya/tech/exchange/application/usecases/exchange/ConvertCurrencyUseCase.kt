package jaya.tech.exchange.application.usecases.exchange

import jaya.tech.exchange.ports.input.rest.dtos.ExchangeResponse
import java.math.BigDecimal
import java.util.*

interface ConvertCurrencyUseCase {
    fun execute(amount: BigDecimal, fromCurrency: String, toCurrency: String, userId: UUID): ExchangeResponse
}
