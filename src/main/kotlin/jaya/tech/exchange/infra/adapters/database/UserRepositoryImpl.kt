package jaya.tech.exchange.infra.adapters.database

import jaya.tech.exchange.application.domain.User
import jaya.tech.exchange.ports.output.persistence.entities.UserModel
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

object UsersTable : Table() {
    val id = uuid("id").autoGenerate()
    val username = varchar("username", 50).uniqueIndex()
    val email = varchar("email", 100)
    val password = varchar("password", 100)
    override val primaryKey = PrimaryKey(id)
}
fun ResultRow.toUserModel() = UserModel(
    id = this[UsersTable.id],
    username = this[UsersTable.username],
    email = this[UsersTable.email],
    password = this[UsersTable.password]
)

class UserRepositoryImpl(private val database: Database) : UserRepository {

    init {
        transaction(database) {
            SchemaUtils.create(UsersTable)
        }
    }

    override fun save(user: User): UserModel = if (user.id != null) {
        update(user.id, user)
    } else {
        create(user)
    }

    override fun getUserByUsername(username: String): UserModel? {
        return transaction(database) {
            UsersTable.select { UsersTable.username eq username }
                .mapNotNull { row -> row.toUserModel() }
                .singleOrNull()
        }
    }

    override fun getById(userId: UUID): UserModel =
        transaction(database) {
            UsersTable.select { UsersTable.id eq userId }
                .map { it.toUserModel() }
                .singleOrNull()
                ?: throw NoSuchElementException("User not found with ID $userId")
        }

    private fun create(user: User): UserModel {
        val userId = UUID.randomUUID()
        runCatching {
            transaction(database) {
                UsersTable.insert {
                    it[id] = userId
                    it[username] = user.username
                    it[email] = user.email
                    it[password] = user.password
                }
            }
        }.onFailure { ex ->
            throw RuntimeException("Error create user $ex.message")
        }
        return UserModel(userId, user.username, user.email, user.password)
    }

    private fun update(userId: UUID,user: User): UserModel {
        runCatching {
            transaction(database) {
                UsersTable.update({ UsersTable.id eq userId }) {
                    it[username] = user.username
                    it[email] = user.email
                }
            }
        }.onFailure{ ex ->
            throw RuntimeException("Error update user id: $userId $ex.message")
        }
        return UserModel(userId, user.username, user.email, user.password)
    }
}