package jaya.tech.exchange.adapters.input.rest.routes

import io.javalin.Javalin

fun Javalin.exchangeRoutes() {
    get("/exchange") { ctx ->
        ctx.status(200)
        ctx.json("Hello World")
    }
}
