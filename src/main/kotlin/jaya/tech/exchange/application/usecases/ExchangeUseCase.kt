package jaya.tech.exchange.application.usecases

import java.math.BigDecimal

interface ExchangeUseCase {
    fun convertCurrency(amount: BigDecimal, fromCurrency: String, toCurrency: String): BigDecimal
}
