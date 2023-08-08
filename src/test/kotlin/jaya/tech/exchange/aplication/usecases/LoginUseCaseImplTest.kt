package jaya.tech.exchange.aplication.usecases

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jaya.tech.exchange.application.usecases.user.LoginUseCaseImpl
import jaya.tech.exchange.ports.output.persistence.entities.UserModel
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository
import org.junit.jupiter.api.assertThrows
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginUseCaseImplTest {

    private val userId = UUID.randomUUID()
    @Test
    fun `Login should return an exception because of invalid username`() {

        val userRepository = mockk<UserRepository>()
        val useCase = LoginUseCaseImpl(userRepository)

        every { userRepository.getUserByUsernameAndPassword("", PASSWORD) } returns null

        assertThrows<Exception> {
            useCase.execute("", PASSWORD)
        }.let { ex ->
            assert(ex.message == LoginUseCaseImpl.INVALID_CREDENTIALS_MESSAGE)
        }

        verify(exactly = 1) { userRepository.getUserByUsernameAndPassword(any(), PASSWORD) }
    }

    @Test
    fun `Login should return an exception because of invalid password`() {
        val userRepository = mockk<UserRepository>()
        val useCase = LoginUseCaseImpl(userRepository)

        every { userRepository.getUserByUsernameAndPassword(USERNAME, "") } returns null

        assertThrows<Exception> {
            useCase.execute(USERNAME, "")
        }.let { ex ->
            assert(ex.message == LoginUseCaseImpl.INVALID_CREDENTIALS_MESSAGE)
        }

        verify(exactly = 1) { userRepository.getUserByUsernameAndPassword(any(), any()) }
    }

    @Test
    fun `should Login with success`() {
        val userRepository = mockk<UserRepository>()
        val useCase = LoginUseCaseImpl(userRepository)
        val userModel = UserModel(
            id = userId,
            username = USERNAME,
            email = EMAIL,
            password = PASSWORD
        )

        every { userRepository.getUserByUsernameAndPassword(USERNAME, PASSWORD) } returns userModel

        val user = useCase.execute(USERNAME, PASSWORD)
        assertEquals(user.id, userModel.id)
        assertEquals(user.username, userModel.username)
        assertEquals(user.email, userModel.email)

        verify(exactly = 1) { userRepository.getUserByUsernameAndPassword(any(), any()) }
    }

    companion object {
        const val USERNAME = "username"
        const val EMAIL = "email@email.com"
        const val PASSWORD = "123"
    }
}
