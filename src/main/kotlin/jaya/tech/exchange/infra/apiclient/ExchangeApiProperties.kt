package jaya.tech.exchange.infra.apiclient

class ExchangeApiProperties {
    var uri: String = System.getenv("EXCHANGE_API_URI")
    var secret: String = System.getenv("EXCHANGE_API_SECRET")
}