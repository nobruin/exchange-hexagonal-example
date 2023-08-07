import io.javalin.Javalin
import jaya.tech.exchange.ports.input.rest.routes.exchangeRoutes
import jaya.tech.exchange.ports.input.rest.routes.userRoutes
import jaya.tech.exchange.infra.adapters.Loggable
import jaya.tech.exchange.infra.modules.appModule
import jaya.tech.exchange.infra.modules.databaseModule
import org.koin.core.context.startKoin

class Application : Loggable {
    fun start() {
        log.info("Starting application")
        startKoin {
            modules(appModule, databaseModule)
        }
        Javalin.create().start(
            System.getenv("API_PORT").toInt()
        ).apply {
            routes {
                userRoutes(API_VERSION)
                exchangeRoutes(API_VERSION)
            }
        }
    }

    companion object{
        const val API_VERSION = "api/v1"
    }
}

fun main() {
    Application().start()
}