import io.javalin.Javalin
import jaya.tech.exchange.adapters.input.rest.routes.exchangeRoutes
import jaya.tech.exchange.adapters.input.rest.routes.userRoutes
import jaya.tech.exchange.infra.Loggable
import jaya.tech.exchange.infra.modules.appModule
import org.koin.core.context.startKoin

class Application : Loggable {
    fun start() {
        log.info("Starting application")
        startKoin {
            modules(appModule)
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