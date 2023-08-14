package jaya.tech.exchange.adapters.rest.dtos

import java.math.BigDecimal
import java.util.UUID

data class ExchangeRequest(
    val amount: BigDecimal,
    val fromCurrency: String,
    val toCurrency: String,
    val userId: UUID
)
