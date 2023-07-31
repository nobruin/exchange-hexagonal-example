package jaya.tech.exchange.ports.output.persistence.repositories

import jaya.tech.exchange.ports.output.persistence.entities.ExchangeModel
import jaya.tech.exchange.domain.Exchange

interface ExchangeRepository {
    fun save(exchange: Exchange): ExchangeModel
}