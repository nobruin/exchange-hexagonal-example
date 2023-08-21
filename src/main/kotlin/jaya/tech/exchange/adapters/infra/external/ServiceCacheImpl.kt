package jaya.tech.exchange.adapters.infra.external

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.util.concurrent.TimeUnit
import jaya.tech.exchange.ports.output.external.exchangeapi.ServiceCache

class ServiceCacheImpl: ServiceCache {
    private val cache: Cache<String, Any> = Caffeine.newBuilder()
        .expireAfterWrite(1, TimeUnit.DAYS)
        .maximumSize(100)
        .build()

    override fun get(key: String): Any? {
        return cache.getIfPresent(key)
    }

    override fun put(key: String, value: Any) {
        cache.put(key, value)
    }

    override fun invalidate(key: String) {
        cache.invalidate(key)
    }
}