package jaya.tech.exchange.adapters.input.rest.controllers

import io.mockk.every
import io.mockk.mockk
import jaya.tech.exchange.adapters.input.rest.dtos.ExchangeRequest
import jaya.tech.exchange.adapters.input.rest.dtos.ExchangeResponse
import jaya.tech.exchange.application.usecases.ExchangeUseCase
import kotlin.test.Test

class ExchangeControllerTest {
    @Test
    fun `Test exchange API endpoint`() {
        val exchangeUseCase = mockk<ExchangeUseCase>()
        val controller = ExchangeController(exchangeUseCase)

        val request = ExchangeRequest(AMOUNT_REQUEST.toBigDecimal(), FROM_CURRENCY, TO_CURRENCY)
        val expectedResponse = ExchangeResponse(AMOUNT_RESPONSE.toBigDecimal())

        every {
            exchangeUseCase.convertCurrency(request.amount, request.fromCurrency, request.toCurrency)
        } returns expectedResponse.result

        val response = controller.exchange(request)

        assert(response.result == expectedResponse.result)
    }

    companion object{
        const val FROM_CURRENCY = "USD"
        const val TO_CURRENCY = "BRL"
        const val AMOUNT_REQUEST = "100.0"
        const val AMOUNT_RESPONSE = "100.0"
    }
}