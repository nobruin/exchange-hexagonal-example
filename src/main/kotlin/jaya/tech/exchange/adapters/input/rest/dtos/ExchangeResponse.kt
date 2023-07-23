package jaya.tech.exchange.adapters.input.rest.dtos

import jaya.tech.exchange.adapters.output.persistence.entities.ExchangeModel
import java.math.BigDecimal

data class ExchangeResponse(
    val amount: BigDecimal,
    val fromCurrency: String,
    val toCurrency: String,
    val userId: Long? = null
)

fun ExchangeModel.toExchangeResponse() = ExchangeResponse(
    fromCurrency = fromCurrency,
    toCurrency = toCurrency,
    userId = userId,
    amount = amount
)