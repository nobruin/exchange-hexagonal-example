package jaya.tech.exchange.ports.input.usecases.exchange

import jaya.tech.exchange.adapters.rest.dtos.ExchangeResponse
import java.math.BigDecimal
import java.util.UUID

interface ConvertCurrencyUseCase {
    fun execute(amount: BigDecimal, fromCurrency: String, toCurrency: String, userId: UUID): ExchangeResponse
}
