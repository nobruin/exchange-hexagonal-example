package jaya.tech.exchange.ports.input.rest.controllers

import io.mockk.every
import io.mockk.mockk
import jaya.tech.exchange.ports.input.rest.dtos.AuthUserDTO
import jaya.tech.exchange.ports.input.rest.dtos.CreateUserRequest
import jaya.tech.exchange.ports.input.rest.dtos.LoginRequest
import jaya.tech.exchange.ports.output.authentication.JwtTokenProvider
import jaya.tech.exchange.application.usecases.user.CreateUserUseCase
import jaya.tech.exchange.application.usecases.user.LoginUseCase
import jaya.tech.exchange.application.domain.User
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserControllerTest {

    private fun getUserController(): Array<Any> {
        val createUserUseCase = mockk<CreateUserUseCase>()
        val loginUseCase = mockk<LoginUseCase>()
        val jwtTokenProvider = mockk<JwtTokenProvider>()
        val controller = UserController(createUserUseCase, loginUseCase, jwtTokenProvider)

        return arrayOf(controller, createUserUseCase, jwtTokenProvider, loginUseCase)
    }

    @Test
    fun `Test create user API endpoint`() {
       val (controller,createUserUseCase,_, _) = getUserController()

        val request = CreateUserRequest(USER_NAME, EMAIL, PASSWORD)
        val user = User(id = USER_ID, username = request.username, email = request.email, password = request.password)

        every {
            (createUserUseCase as CreateUserUseCase).execute(USER_NAME, EMAIL, PASSWORD)
        }.returns(user)

        val response = (controller as UserController).createUser(request)

        assertEquals(response.email, user.email)
        assertEquals(response.username, user.username)
        assertEquals(response.id, user.id)
    }

    @Test
    fun `Test login API endpoint`() {
        val (controller,_,jwtProvider, loginUseCase ) = getUserController()
        val request = LoginRequest(USER_NAME, PASSWORD)
        val authUserDTO = AuthUserDTO(id = USER_ID, username = request.username, email = EMAIL)

        every {
            (loginUseCase as LoginUseCase).execute(USER_NAME, PASSWORD)
        }.returns(authUserDTO)
        every {
            (jwtProvider as JwtTokenProvider).createToken(authUserDTO)
        }.returns(JWT_TOKEN)

        val response = (controller as UserController).login(request)

        assertEquals(response, JWT_TOKEN)
    }



    companion object{
        const val USER_NAME = "user_name"
        const val PASSWORD = "secret"
        const val EMAIL = "test@test.com"
        const val USER_ID = 1L
        const val JWT_TOKEN = "jwtTokenString"
    }
}