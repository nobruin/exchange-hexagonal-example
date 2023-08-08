package jaya.tech.exchange.ports.input.rest.routes

import io.javalin.Javalin

fun Javalin.exchangeRoutes(version: String) {
    get("$version/exchange") { ctx ->
        ctx.status(200)
        ctx.json("Hello World")
    }
}
