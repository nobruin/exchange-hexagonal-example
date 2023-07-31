package jaya.tech.exchange.ports.output.external.exchangeapi.entities

data class ExchangeResult(
    var success: Boolean = false,
    var timestamp: Long = 0,
    var base: String = "",
    var date: String = "",
    var rates: Map<String, Double> = mutableMapOf()
)