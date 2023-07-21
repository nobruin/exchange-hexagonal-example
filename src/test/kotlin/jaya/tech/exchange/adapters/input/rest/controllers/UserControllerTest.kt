package jaya.tech.exchange.adapters.input.rest.controllers

import io.mockk.every
import io.mockk.mockk
import jaya.tech.exchange.adapters.input.rest.dtos.CreateUserRequest
import jaya.tech.exchange.application.usecases.user.CreateUserUseCase
import jaya.tech.exchange.domain.User
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserControllerTest {

    @Test
    fun `Test create user API endpoint`() {
        val createUserUseCase = mockk<CreateUserUseCase>()
        val controller = UserController(createUserUseCase)

        val request = CreateUserRequest(USER_NAME, EMAIL, PASSWORD)
        val user = User(id = USER_ID, username = request.username, email = request.email, password = request.password)

        every {
            createUserUseCase.execute(USER_NAME, EMAIL, PASSWORD)
        }.returns(user)

        val response = controller.createUser(request)

        assertEquals(response.email, user.email)
        assertEquals(response.username, user.username)
        assertEquals(response.id, user.id)
    }


    companion object{
        const val USER_NAME = "user_name"
        const val PASSWORD = "secret"
        const val EMAIL = "test@test.com"
        const val USER_ID = 1L
    }
}