package jaya.tech.exchange.application.usecases.exchange

import java.math.BigDecimal

interface ConvertCurrencyUseCase {
    fun execute(amount: BigDecimal, fromCurrency: String, toCurrency: String): BigDecimal
}
