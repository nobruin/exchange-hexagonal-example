package jaya.tech.exchange.infra.apiclient

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import jaya.tech.exchange.adapters.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.adapters.output.external.exchangeapi.entities.ExchangeResult
import jaya.tech.exchange.infra.Loggable


class ExchangeApiGatewayImpl(
    private val properties: ExchangeApiProperties,
) : ExchangeGateway, Loggable {
    override fun findRates(fromCurrency: String, toCurrency: String): ExchangeResult = runCatching {
        Fuel.get(
            properties.uri + ROUTE,
            listOf(ACCESS_KEY to properties.secret, SYMBOLS to "${fromCurrency},${toCurrency}")
        ).responseObject<ExchangeResult>().third.get().also {
            log.info("Rates founded: $it")
        }
    }.onFailure { error ->
        log.error("Error on find rates", error)
        throw error
    }.getOrThrow()

    companion object {
        const val ACCESS_KEY = "access_key"
        const val SYMBOLS = "symbols"
        private const val API_VERSION = "/v1"
        const val ROUTE = "$API_VERSION/latest"
    }
}