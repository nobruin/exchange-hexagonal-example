import io.javalin.Javalin
import jaya.tech.exchange.adapters.rest.handleException
import jaya.tech.exchange.adapters.rest.routes.exchangeRoutes
import jaya.tech.exchange.adapters.rest.routes.userRoutes
import jaya.tech.exchange.infra.Loggable
import jaya.tech.exchange.infra.modules.appModule
import jaya.tech.exchange.infra.modules.databaseModule
import org.koin.core.context.startKoin

class Application : Loggable {
    fun start() {
        log.info("Starting application")
        startKoin {
            modules(databaseModule, appModule)
        }
        Javalin.create().start(
            System.getenv("API_PORT").toInt()
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
