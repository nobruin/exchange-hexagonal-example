package jaya.tech.exchange.application.domain

import java.math.BigDecimal
import java.util.UUID

data class Exchange(
    val id: UUID? = null,
    val userId: UUID? = null,
    val amount: BigDecimal,
    val fromCurrency: String,
    val toCurrency: String,
    val result: BigDecimal
)
