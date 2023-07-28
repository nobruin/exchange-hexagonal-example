import io.javalin.Javalin
import jaya.tech.exchange.adapters.input.rest.routes.exchangeRoutes
import jaya.tech.exchange.adapters.input.rest.routes.userRoutes

fun main() {
    val app = Javalin.create().start(8080)

    // Register routes
    app.exchangeRoutes()
    app.userRoutes()
}