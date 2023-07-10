package jaya.tech.exchange.adapters.output.persistence.repositories

import jaya.tech.exchange.adapters.output.persistence.entities.ExchangeModel
import jaya.tech.exchange.domain.Exchange

interface ExchangeRepository {
    fun save(exchange: Exchange): ExchangeModel
}