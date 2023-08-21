package jaya.tech.exchange.ports.output.external.exchangeapi

import jaya.tech.exchange.ports.output.external.exchangeapi.entities.ExchangeResult

interface ExchangeGateway {
    fun getExchangeRate(fromCurrency: String, toCurrency: String): ExchangeResult
}
