package jaya.tech.exchange.ports.output.persistence.entities

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID

class ExchangeModel(
    val id: UUID? = null,
    val userId: UUID? = null,
    val amount: BigDecimal = "0.0".toBigDecimal(),
    val fromCurrency: String = "",
    val toCurrency: String = "",
    var rateConversion: BigDecimal? = null,
    var convertedAmount: BigDecimal = "0.0".toBigDecimal(),
    val createdAt: OffsetDateTime = LocalDateTime.now().atOffset(ZoneOffset.UTC)
)