package jaya.tech.exchange.infra.apiclient

class ExchangeApiProperties {
    var uri: String = System.getProperty("exchange.api.uri")
    var secret: String = System.getProperty("exchange.api.secret")
}