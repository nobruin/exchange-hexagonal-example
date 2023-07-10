package jaya.tech.exchange.adapters.output.external.exchangeapi

import jaya.tech.exchange.adapters.output.external.exchangeapi.entities.ExchangeResult

interface ExchangeGateway {
    fun findRates(fromCurrency: String, toCurrency: String): ExchangeResult
}