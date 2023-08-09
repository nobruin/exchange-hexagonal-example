package jaya.tech.exchange.adapters.rest.routes

import io.javalin.Javalin

fun Javalin.exchangeRoutes(version: String) {
    get("$version/exchange") { ctx ->
        ctx.status(200)
        ctx.json("Hello World")
    }
}
