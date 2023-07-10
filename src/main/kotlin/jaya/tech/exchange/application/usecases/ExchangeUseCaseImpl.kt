package jaya.tech.exchange.application.usecases

import jaya.tech.exchange.adapters.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.adapters.output.persistence.repositories.ExchangeRepository
import jaya.tech.exchange.domain.Exchange
import java.lang.RuntimeException
import java.math.BigDecimal

class ExchangeUseCaseImpl(
    private val exchangeGateway: ExchangeGateway,
    private val exchangeRepository: ExchangeRepository
): ExchangeUseCase {
    override fun convertCurrency(amount: BigDecimal, fromCurrency: String, toCurrency: String): BigDecimal {
        // get rates
        val exchangeResult = exchangeGateway.findRates(fromCurrency, toCurrency)
        //calculate
        val rateConversion =
            calculateConversionRate(exchangeResult.rates[fromCurrency], exchangeResult.rates[toCurrency])
        val result = calculateAmount(amount, rateConversion)
        //save
        exchangeRepository.save(Exchange(
            amount = amount,
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            result = result
        ))
        // return
        return result
    }

    private fun calculateConversionRate(currency: Double?, destinyCurrency: Double?): BigDecimal? {
        when{
            currency == null || currency <= 0.0 -> throw RuntimeException(RUNTIME_EXCEPTION_MESSAGE)
            destinyCurrency == null || destinyCurrency <= 0.0 -> throw RuntimeException(RUNTIME_EXCEPTION_MESSAGE)
        }
        return (destinyCurrency?.div(currency!!))?.toBigDecimal()
    }

    private fun calculateAmount(amount: BigDecimal, rateConversion: BigDecimal?): BigDecimal {
        if(rateConversion == null || rateConversion <= "0.0".toBigDecimal()){
            throw RuntimeException("value is invalid for this operation")
        }
        return amount.let { rateConversion.times(it) }
    }

    companion object{
        const val RUNTIME_EXCEPTION_MESSAGE = "values or Values is invalid for this operation"
    }
}
