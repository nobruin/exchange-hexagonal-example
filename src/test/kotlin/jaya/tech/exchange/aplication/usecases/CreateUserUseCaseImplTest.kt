package jaya.tech.exchange.aplication.usecases

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jaya.tech.exchange.application.domain.toEntity
import jaya.tech.exchange.application.usecases.user.CreateUserUseCaseIMpl
import jaya.tech.exchange.ports.output.persistence.entities.UserModel
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateUserUseCaseImplTest {

    @Test
    fun `should create User with Invalid username`() {

        val userRepository = mockk<UserRepository>()
        val useCase = CreateUserUseCaseIMpl(userRepository)

        assertThrows<Exception> {
            useCase.execute("", EMAIL, PASSWORD)
        }.let { ex ->
            assert(ex.message == CreateUserUseCaseIMpl.USER_INVALID_MESSAGE)
        }

        verify(exactly = 0) { userRepository.getUserByUsername(any()) }
        verify(exactly = 0) { userRepository.createUser(any())}
    }

    @Test
    fun `should create User with Invalid email`() {
        val userRepository = mockk<UserRepository>()
        val useCase = CreateUserUseCaseIMpl(userRepository)

        assertThrows<Exception> {
            useCase.execute(USERNAME, "", PASSWORD)
        }.let { ex ->
            assert(ex.message == CreateUserUseCaseIMpl.USER_INVALID_MESSAGE)
        }

        verify(exactly = 0) { userRepository.getUserByUsername(any()) }
        verify(exactly = 0) { userRepository.createUser(any())}
    }

    @Test
    fun `should create User with Invalid password`() {
        val userRepository = mockk<UserRepository>()
        val useCase = CreateUserUseCaseIMpl(userRepository)

        assertThrows<Exception> {
            useCase.execute(USERNAME, EMAIL, "")
        }.let { ex ->
            assert(ex.message == CreateUserUseCaseIMpl.USER_INVALID_MESSAGE)
        }

        verify(exactly = 0) { userRepository.getUserByUsername(any()) }
        verify(exactly = 0) { userRepository.createUser(any())}
    }

    @Test
    fun `should create User with repository error`() {
        val userRepository = mockk<UserRepository>()
        val useCase = CreateUserUseCaseIMpl(userRepository)
        val userModel = UserModel(
            id = USER_ID,
            username = USERNAME,
            email = EMAIL,
            password = PASSWORD
        )

        every { userRepository.getUserByUsername(USERNAME) } returns userModel

        assertThrows<Exception> {
              useCase.execute(USERNAME, EMAIL, PASSWORD)
        }.let { ex ->
            assert(ex.message == CreateUserUseCaseIMpl.USER_ALREADY_EXISTS_MESSAGE)
        }

        verify(exactly = 1) { userRepository.getUserByUsername(any()) }
        verify(exactly = 0) { userRepository.createUser(any())}
    }

    @Test
    fun `should create User with success`() {
        val userRepository = mockk<UserRepository>()
        val useCase = CreateUserUseCaseIMpl(userRepository)
        val userModel = UserModel(
            id = USER_ID,
            username = USERNAME,
            email = EMAIL,
            password = PASSWORD
        )

        every { userRepository.getUserByUsername(USERNAME) } returns null
        every { userRepository.createUser(any()) } returns userModel

        val user = useCase.execute(USERNAME, EMAIL, PASSWORD)
        assertEquals(user, userModel.toEntity())

        verify(exactly = 1) { userRepository.getUserByUsername(any()) }
        verify(exactly = 1) { userRepository.createUser(any()) }
    }

    companion object{
        const val USER_ID = 1L
        const val USERNAME = "username"
        const val EMAIL = "email@email.com"
        const val PASSWORD = "123"
    }
}