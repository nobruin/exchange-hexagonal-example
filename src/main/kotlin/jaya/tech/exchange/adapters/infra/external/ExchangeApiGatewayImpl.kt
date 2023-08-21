package jaya.tech.exchange.adapters.infra.external

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import jaya.tech.exchange.application.Loggable
import jaya.tech.exchange.ports.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.ports.output.external.exchangeapi.ServiceCache
import jaya.tech.exchange.ports.output.external.exchangeapi.entities.ExchangeResult

class ExchangeApiGatewayImpl(
    private val properties: ExchangeApiProperties,
    private val cache: ServiceCache,
) : ExchangeGateway, Loggable {
    override fun getExchangeRate(fromCurrency: String, toCurrency: String): ExchangeResult = runCatching {
        val cacheKey = "$fromCurrency-$toCurrency"
        cache.get(cacheKey).let {
            if(it != null){
                return@runCatching it as ExchangeResult
            }
        }

        Fuel.get(
            properties.uri + ROUTE,
            listOf(ACCESS_KEY to properties.secret, SYMBOLS to "$fromCurrency,$toCurrency")
        ).responseObject<ExchangeResult>().third.get().also {
            log.info("Rates founded: $it")
            cache.put(cacheKey, it)
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
