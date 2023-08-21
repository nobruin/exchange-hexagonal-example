package jaya.tech.exchange.ports.output.persistence.repositories

import jaya.tech.exchange.application.domain.Exchange
import jaya.tech.exchange.ports.output.persistence.entities.ExchangeModel

interface ExchangeRepository {
    fun save(exchange: Exchange): ExchangeModel
}
