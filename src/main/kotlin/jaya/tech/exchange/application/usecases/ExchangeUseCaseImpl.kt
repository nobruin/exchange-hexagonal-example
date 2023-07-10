package jaya.tech.exchange.application.usecases

import jaya.tech.exchange.adapters.output.external.exchangeapi.ExchangeGateway
import java.lang.RuntimeException
import java.math.BigDecimal

class ExchangeUseCaseImpl(
    private val exchangeGateway: ExchangeGateway
): ExchangeUseCase {
    override fun convertCurrency(amount: BigDecimal, fromCurrency: String, toCurrency: String): BigDecimal {
        // get rates
        val exchangeResult = exchangeGateway.findRates(fromCurrency, toCurrency)
        val rateConversion =
            calculateConversionRate(exchangeResult.rates[fromCurrency], exchangeResult.rates[toCurrency])
        //calculate
        //save

        // return

        return calculateAmount(amount, rateConversion)
    }

    private fun calculateConversionRate(currency: Double?, destinyCurrency: Double?): BigDecimal? {
        when{
            currency == null || currency <= 0.0 -> throw RuntimeException("values or Values is invalid for this operation")
            destinyCurrency == null || destinyCurrency <= 0.0 -> throw RuntimeException("values or Values is invalid for this operation")
        }
        return (destinyCurrency?.div(currency!!))?.toBigDecimal()
    }

    private fun calculateAmount(amount: BigDecimal, rateConversion: BigDecimal?): BigDecimal {
        if(rateConversion == null || rateConversion <= "0.0".toBigDecimal()){
            throw RuntimeException("value is invalid for this operation")
        }
        return amount.let { rateConversion.times(it) }
    }
}
