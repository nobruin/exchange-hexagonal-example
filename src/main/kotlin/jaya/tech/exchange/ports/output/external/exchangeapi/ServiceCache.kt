package jaya.tech.exchange.ports.output.external.exchangeapi

interface ServiceCache {
    fun get(key: String): Any?
    fun put(key: String, value: Any)
    fun invalidate(key: String)
}