package jaya.tech.exchange.ports.input.rest.controllers

import io.mockk.every
import io.mockk.mockk
import jaya.tech.exchange.adapters.rest.controllers.ExchangeController
import jaya.tech.exchange.adapters.rest.dtos.AuthUserDTO
import jaya.tech.exchange.adapters.rest.dtos.ExchangeRequest
import jaya.tech.exchange.adapters.rest.dtos.ExchangeResponse
import jaya.tech.exchange.ports.input.authentication.JwtTokenProvider
import jaya.tech.exchange.ports.input.usecases.exchange.ConvertCurrencyUseCase
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class ExchangeControllerTest {

    private val userId = UUID.randomUUID()

    @Test
    fun `Test conversion API endpoint with invalid token`() {
        val convertCurrencyUseCase = mockk<ConvertCurrencyUseCase>()
        val jwtTokenProvider = mockk<JwtTokenProvider>()
        val controller = ExchangeController(convertCurrencyUseCase, jwtTokenProvider)

        val request = ExchangeRequest(AMOUNT_REQUEST.toBigDecimal(), FROM_CURRENCY, TO_CURRENCY, userId)
        val expectedResponse = ExchangeResponse(
            amount = request.amount,
            toCurrency = request.toCurrency,
            fromCurrency = request.toCurrency,
            userId = request.userId
        )

        every {
            convertCurrencyUseCase.execute(request.amount, request.fromCurrency, request.toCurrency, request.userId)
        } returns expectedResponse

        assertThrows<Exception> {
            controller.exchange(JWT_TOKEN, request)
        }
    }

    @Test
    fun `Test conversion API endpoint invalid User`() {
        val convertCurrencyUseCase = mockk<ConvertCurrencyUseCase>()
        val jwtTokenProvider = mockk<JwtTokenProvider>()
        val controller = ExchangeController(convertCurrencyUseCase, jwtTokenProvider)

        val request = ExchangeRequest(AMOUNT_REQUEST.toBigDecimal(), FROM_CURRENCY, TO_CURRENCY, userId)

        every {
            convertCurrencyUseCase.execute(request.amount, request.fromCurrency, request.toCurrency, request.userId)
        } throws Exception("Invalid User")

        assertThrows<Exception> {
            controller.exchange(JWT_TOKEN, request)
        }
    }

    @Test
    fun `Test conversion API with success`() {
        val convertCurrencyUseCase = mockk<ConvertCurrencyUseCase>()
        val jwtTokenProvider = mockk<JwtTokenProvider>()
        val controller = ExchangeController(convertCurrencyUseCase, jwtTokenProvider)
        val authUserDTO = AuthUserDTO(
            id = userId,
            username = USERNAME,
            email = LOGIN
        )

        val request = ExchangeRequest(AMOUNT_REQUEST.toBigDecimal(), FROM_CURRENCY, TO_CURRENCY, userId)
        val expectedResponse = ExchangeResponse(
            amount = request.amount,
            toCurrency = request.toCurrency,
            fromCurrency = request.toCurrency,
            userId = authUserDTO.id
        )

        every {
            convertCurrencyUseCase.execute(request.amount, request.fromCurrency, request.toCurrency, request.userId)
        } returns expectedResponse

        every { jwtTokenProvider.getUserFromToken(JWT_TOKEN) } returns authUserDTO

        val response = controller.exchange(JWT_TOKEN, request)

        assertEquals(response, expectedResponse)
        if (response != null) {
            assertEquals(response.userId, authUserDTO.id)
        }
    }

    companion object {
        const val FROM_CURRENCY = "USD"
        const val TO_CURRENCY = "BRL"
        const val AMOUNT_REQUEST = "100.0"
        const val JWT_TOKEN = "jwtTokenString"
        const val USERNAME = "username"
        const val LOGIN = "login"
    }
}
