package jaya.tech.exchange.domain

import java.math.BigDecimal

data class Exchange(
    val amount: BigDecimal,
    val fromCurrency: String,
    val toCurrency: String,
    val result: BigDecimal
)
