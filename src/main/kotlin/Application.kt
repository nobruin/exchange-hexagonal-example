
import io.github.cdimascio.dotenv.dotenv
import io.javalin.Javalin
import jaya.tech.exchange.adapters.infra.dependency_injection.appModule
import jaya.tech.exchange.adapters.infra.dependency_injection.databaseModule
import jaya.tech.exchange.adapters.rest.handleException
import jaya.tech.exchange.adapters.rest.routes.exchangeRoutes
import jaya.tech.exchange.adapters.rest.routes.userRoutes
import jaya.tech.exchange.application.Loggable
import org.koin.core.context.startKoin

class Application : Loggable {
    fun start() {
        val env = dotenv {
            directory = "assets"
            filename = ".env"
            ignoreIfMissing = true
        }
        log.info("Starting application")
        startKoin {
            modules(databaseModule, appModule)
        }
        Javalin.create().start(
            env["API_PORT"]?.toInt() ?: 7000
        ).apply {
            routes {
                userRoutes(API_VERSION)
                exchangeRoutes(API_VERSION)
            }
        }.exception(Exception::class.java) { e, ctx ->
            handleException(e, ctx)
        }
    }

    companion object {
        const val API_VERSION = "api/v1"
    }
}

fun main() {
    Application().start()
}
