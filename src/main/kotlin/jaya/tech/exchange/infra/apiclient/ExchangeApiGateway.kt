package jaya.tech.exchange.infra.apiclient

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import jaya.tech.exchange.adapters.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.adapters.output.external.exchangeapi.entities.ExchangeResult


class ExchangeApiGateway(
    private val properties: ExchangeApiProperties,
    ): ExchangeGateway {
    override fun findRates(fromCurrency: String, toCurrency: String): ExchangeResult
     = Fuel.get(
        properties.uri+"/latest",
        listOf(ACCESS_KEY to properties.secret, SYMBOLS to "${fromCurrency},${toCurrency}")
    ).responseObject<ExchangeResult>().second as ExchangeResult

    companion object{
        const val ACCESS_KEY = "access_key"
        const val SYMBOLS = "symbols"
    }
}