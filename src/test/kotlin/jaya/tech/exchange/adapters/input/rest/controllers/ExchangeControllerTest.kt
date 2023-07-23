package jaya.tech.exchange.adapters.input.rest.controllers

import io.mockk.every
import io.mockk.mockk
import jaya.tech.exchange.adapters.input.rest.dtos.ExchangeRequest
import jaya.tech.exchange.adapters.input.rest.dtos.ExchangeResponse
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCase
import kotlin.test.Test
import kotlin.test.assertEquals

class ExchangeControllerTest {
    @Test
    fun `Test conversion API endpoint`() {
        val convertCurrencyUseCase = mockk<ConvertCurrencyUseCase>()
        val controller = ExchangeController(convertCurrencyUseCase)

        val request = ExchangeRequest(AMOUNT_REQUEST.toBigDecimal(), FROM_CURRENCY, TO_CURRENCY, USER_ID)
        val expectedResponse = ExchangeResponse(
            amount = request.amount,
            toCurrency = request.toCurrency,
            fromCurrency = request.toCurrency,
            userId = request.userId
        )

        every {
            convertCurrencyUseCase.execute(request.amount, request.fromCurrency, request.toCurrency, request.userId)
        } returns expectedResponse

        val response = controller.exchange(request)

        assertEquals(response, expectedResponse)
    }

    companion object{
        const val USER_ID = 1L
        const val FROM_CURRENCY = "USD"
        const val TO_CURRENCY = "BRL"
        const val AMOUNT_REQUEST = "100.0"
    }
}