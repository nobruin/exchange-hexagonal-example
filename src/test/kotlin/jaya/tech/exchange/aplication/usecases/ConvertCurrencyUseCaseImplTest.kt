package jaya.tech.exchange.aplication.usecases

import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import jaya.tech.exchange.ports.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.ports.output.external.exchangeapi.entities.ExchangeResult
import jaya.tech.exchange.ports.output.persistence.entities.ExchangeModel
import jaya.tech.exchange.ports.output.persistence.repositories.ExchangeRepository
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCaseImpl
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class ConvertCurrencyUseCaseImplTest {

    private val userId = UUID.randomUUID()
    private val exchangeID = UUID.randomUUID()

    @Test
    fun `should convert currency with gateway error`() {
        val exchangeGateway: ExchangeGateway = mockk<ExchangeGateway>()
        val exchangeRepository: ExchangeRepository = mockk<ExchangeRepository>()
        val useCase = ConvertCurrencyUseCaseImpl(exchangeGateway, exchangeRepository)

        every { exchangeGateway.findRates(any(), any()) } throws RuntimeException("error")

        assertThrows<RuntimeException> {
            useCase.execute(1.0.toBigDecimal(), FROM_CURRENCY, TO_CURRENCY, userId)
        }
    }

    @Test
    fun `should convert currency with repository error`() {
        val exchangeGateway: ExchangeGateway = mockk<ExchangeGateway>()
        val exchangeRepository: ExchangeRepository = mockk<ExchangeRepository>()
        val useCase = ConvertCurrencyUseCaseImpl(exchangeGateway, exchangeRepository)

        every { exchangeGateway.findRates("USD", "BRL") } returns ExchangeResult(
            success = EXCHANGE_STATUS,
            timestamp = TIMESTAMP,
            base = FROM_CURRENCY,
            date = "2021-01-01",
            rates = mapOf(FROM_CURRENCY to 1.0, TO_CURRENCY to 5.0)
        )

        every { exchangeRepository.save(any()) } throws RuntimeException("error")

        assertThrows<RuntimeException> {
            useCase.execute(1.0.toBigDecimal(), FROM_CURRENCY, TO_CURRENCY, userId)
        }
    }

    @Test
    fun `should convert currency with success`() {
        val exchangeGateway: ExchangeGateway = mockk<ExchangeGateway>()
        val exchangeRepository: ExchangeRepository = mockk<ExchangeRepository>()
        val useCase = ConvertCurrencyUseCaseImpl(exchangeGateway, exchangeRepository)
        val exchangeModel = ExchangeModel(
            id = exchangeID,
            userId = userId,
            amount = AMOUNT_REQUESTED.toBigDecimal(),
            fromCurrency = FROM_CURRENCY,
            toCurrency = TO_CURRENCY,
            convertedAmount = 5.0.toBigDecimal()
        )
        val exchangeResult = ExchangeResult(
            success = EXCHANGE_STATUS,
            timestamp = TIMESTAMP,
            base = FROM_CURRENCY,
            date = "2021-01-01",
            rates = mapOf(FROM_CURRENCY to 1.0, TO_CURRENCY to 5.0)
        )

        every { exchangeGateway.findRates("USD", "BRL") } returns exchangeResult
        every { exchangeRepository.save(any()) } returns exchangeModel

        useCase.execute(AMOUNT_REQUESTED.toBigDecimal(), FROM_CURRENCY, TO_CURRENCY, userId).let { result ->
            assertEquals(result.userId, exchangeModel.userId)
            assertEquals(result.fromCurrency, exchangeModel.fromCurrency)
            assertEquals(result.toCurrency, exchangeModel.toCurrency)
            assertEquals(result.amount, AMOUNT_REQUESTED.toBigDecimal())
        }
    }

    companion object{
        const val FROM_CURRENCY = "USD"
        const val TO_CURRENCY = "BRL"
        const val AMOUNT_REQUESTED = 1.0
        const val EXCHANGE_STATUS = true
        const val TIMESTAMP = 1L
    }
}