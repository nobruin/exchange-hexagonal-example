package jaya.tech.exchange.adapters.infra.external

import io.github.cdimascio.dotenv.Dotenv
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ExchangeApiProperties : KoinComponent {
    private val env by inject<Dotenv>()

    var uri: String? = env["EXCHANGE_API_URI"]
    var secret: String? = env["EXCHANGE_API_SECRET"]
}
