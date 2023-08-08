package jaya.tech.exchange.application.usecases.exchange

import jaya.tech.exchange.application.domain.Exchange
import jaya.tech.exchange.infra.adapters.Loggable
import jaya.tech.exchange.ports.input.rest.dtos.ExchangeResponse
import jaya.tech.exchange.ports.input.rest.dtos.toExchangeResponse
import jaya.tech.exchange.ports.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.ports.output.persistence.repositories.ExchangeRepository
import java.lang.RuntimeException
import java.math.BigDecimal
import java.util.UUID

class ConvertCurrencyUseCaseImpl(
    private val exchangeGateway: ExchangeGateway,
    private val exchangeRepository: ExchangeRepository
) : ConvertCurrencyUseCase, Loggable {
    override fun execute(amount: BigDecimal, fromCurrency: String, toCurrency: String, userId: UUID): ExchangeResponse {
        log.info("convert currency from $fromCurrency to $toCurrency with amount $amount and userId $userId")
        val exchangeResult = exchangeGateway.findRates(fromCurrency, toCurrency)

        val rateConversion =
            calculateConversionRate(exchangeResult.rates[fromCurrency], exchangeResult.rates[toCurrency])
        val result = calculateAmount(amount, rateConversion)

        val exchangeModel = exchangeRepository.save(
            Exchange(
                userId = userId,
                amount = amount,
                fromCurrency = fromCurrency,
                toCurrency = toCurrency,
                result = result
            )
        )
        return exchangeModel.toExchangeResponse()
    }

    private fun calculateConversionRate(currency: Double?, destinyCurrency: Double?): BigDecimal? {
        when {
            currency == null || currency <= 0.0 -> throw RuntimeException(BAD_REQUEST_EXCEPTION_MESSAGE).also {
                log.error(BAD_REQUEST_EXCEPTION_MESSAGE)
            }
            destinyCurrency == null || destinyCurrency <= 0.0 -> throw RuntimeException(BAD_REQUEST_EXCEPTION_MESSAGE).also {
                log.error(BAD_REQUEST_EXCEPTION_MESSAGE)
            }
        }
        return (destinyCurrency?.div(currency!!))?.toBigDecimal()
    }

    private fun calculateAmount(amount: BigDecimal, rateConversion: BigDecimal?): BigDecimal {
        rateConversion ?: throw RuntimeException(BAD_REQUEST_EXCEPTION_MESSAGE_RATE_NOT_FOUND).also {
            log.error(BAD_REQUEST_EXCEPTION_MESSAGE_RATE_NOT_FOUND)
        }
        return amount.let { rateConversion.times(it) }
    }

    companion object {
        const val BAD_REQUEST_EXCEPTION_MESSAGE = "values or Values is invalid for conversion"
        const val BAD_REQUEST_EXCEPTION_MESSAGE_RATE_NOT_FOUND = "Rate of conversion not found"
    }
}
