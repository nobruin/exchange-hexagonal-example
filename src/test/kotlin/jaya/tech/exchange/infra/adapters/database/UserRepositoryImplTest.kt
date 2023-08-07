package jaya.tech.exchange.infra.adapters.database

import jaya.tech.exchange.application.domain.User
import jaya.tech.exchange.application.domain.toEntity
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UserRepositoryTest {

    private lateinit var database: Database
    private lateinit var userRepository: UserRepositoryImpl

    @BeforeEach
    fun setup() {
        database = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        userRepository = UserRepositoryImpl(database)
    }

    @AfterEach
    fun tearDown() {
        database.connector().close()
    }

    @Test
    fun `should be create, get and update User`() {
        val user = User( null, USERNAME, USERNAME, PASSWORD)
        val createdUser = userRepository.save(user)

        assertEquals(user.username, createdUser.username)
        assertEquals(user.email, createdUser.email)

        var retrievedUser = userRepository.getById(createdUser.id!!)
        assertEquals(createdUser, retrievedUser)

        retrievedUser = retrievedUser.copy(username = "test2", email = "test@test.com")

        val updatedUser = userRepository.save(retrievedUser.toEntity())
        assertEquals(retrievedUser, updatedUser)
    }

    @Test
    fun `should create user and get user by username`() {
        val user = User( null, USERNAME, EMAIL, PASSWORD)
        userRepository.save(user)

        val retrievedUser = userRepository.getUserByUsername(USERNAME)
        assertEquals(user.username, retrievedUser?.username)
        assertEquals(user.email, retrievedUser?.email)
    }

    @Test
    fun `should get username is null`() {
        val retrievedUser = userRepository.getUserByUsername("nonexistent")
        assertNull(retrievedUser)
    }

    companion object{
        const val USERNAME = "username"
        const val EMAIL = "email@email.com"
        const val PASSWORD = "12345"
    }
}