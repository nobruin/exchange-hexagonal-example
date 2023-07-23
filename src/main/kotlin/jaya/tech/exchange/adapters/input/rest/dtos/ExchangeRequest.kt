package jaya.tech.exchange.adapters.input.rest.dtos

import java.math.BigDecimal

data class ExchangeRequest(val amount: BigDecimal, val fromCurrency: String, val toCurrency: String, val userId: Long)
