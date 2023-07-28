import io.javalin.Javalin
import jaya.tech.exchange.adapters.input.rest.routes.exchangeRoutes
import jaya.tech.exchange.adapters.input.rest.routes.userRoutes
import jaya.tech.exchange.infra.dependency_injection.appModule
import org.koin.core.context.startKoin

fun main() {

    // DI initialization
    startKoin {
        modules(appModule)
    }

    val app = Javalin.create().start(8080)
    // Register routes
    app.exchangeRoutes()
    app.userRoutes()

}