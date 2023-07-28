package jaya.tech.exchange.infra.dependency_injection

import jaya.tech.exchange.adapters.input.rest.controllers.ExchangeController
import jaya.tech.exchange.adapters.input.rest.controllers.UserController
import jaya.tech.exchange.adapters.output.authentication.JwtTokenProvider
import jaya.tech.exchange.adapters.output.authentication.JwtTokenProviderImpl
import jaya.tech.exchange.adapters.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCase
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCaseImpl
import jaya.tech.exchange.infra.apiclient.ExchangeApiGatewayImpl
import org.koin.dsl.module


//TODO: Add all dependencies here
var appModule = module {
    single<ExchangeGateway> { ExchangeApiGatewayImpl(get()) }
    single<ConvertCurrencyUseCase> { ConvertCurrencyUseCaseImpl(get(), get()) }
    single<JwtTokenProvider> { JwtTokenProviderImpl() }
    single { ExchangeController(get(), get()) }
    single { UserController(get(),get(), get()) }
}