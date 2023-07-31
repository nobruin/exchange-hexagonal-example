package jaya.tech.exchange.infra.adapters.modules

import jaya.tech.exchange.ports.input.rest.controllers.ExchangeController
import jaya.tech.exchange.ports.input.rest.controllers.UserController
import jaya.tech.exchange.ports.output.authentication.JwtTokenProvider
import jaya.tech.exchange.infra.adapters.authentication.JwtTokenProviderImpl
import jaya.tech.exchange.ports.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCase
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCaseImpl
import jaya.tech.exchange.application.usecases.user.CreateUserUseCase
import jaya.tech.exchange.application.usecases.user.CreateUserUseCaseIMpl
import jaya.tech.exchange.application.usecases.user.LoginUseCase
import jaya.tech.exchange.application.usecases.user.LoginUseCaseImpl
import jaya.tech.exchange.infra.adapters.apiclient.ExchangeApiGatewayImpl
import jaya.tech.exchange.infra.adapters.database.UserRepositoryImpl
import org.koin.dsl.module

var appModule = module {
    single<ExchangeGateway> { ExchangeApiGatewayImpl(get()) }
    single<CreateUserUseCase> { CreateUserUseCaseIMpl(get()) }
    factory<JwtTokenProvider> { JwtTokenProviderImpl() }
    single<LoginUseCase> { LoginUseCaseImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    single<ConvertCurrencyUseCase> { ConvertCurrencyUseCaseImpl(get(), get()) }
    factory { ExchangeController(get(), get()) }
    factory { UserController(get(),get(), get()) }
}