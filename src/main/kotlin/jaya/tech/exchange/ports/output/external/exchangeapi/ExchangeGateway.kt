package jaya.tech.exchange.ports.output.external.exchangeapi

import jaya.tech.exchange.ports.output.external.exchangeapi.entities.ExchangeResult

interface ExchangeGateway {
    fun findRates(fromCurrency: String, toCurrency: String): ExchangeResult
}
