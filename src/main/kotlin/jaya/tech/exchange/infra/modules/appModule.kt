package jaya.tech.exchange.infra.modules

import jaya.tech.exchange.adapters.input.rest.controllers.ExchangeController
import jaya.tech.exchange.adapters.input.rest.controllers.UserController
import jaya.tech.exchange.adapters.output.authentication.JwtTokenProvider
import jaya.tech.exchange.infra.authentication.JwtTokenProviderImpl
import jaya.tech.exchange.adapters.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.adapters.output.persistence.repositories.UserRepository
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCase
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCaseImpl
import jaya.tech.exchange.application.usecases.user.CreateUserUseCase
import jaya.tech.exchange.application.usecases.user.CreateUserUseCaseIMpl
import jaya.tech.exchange.application.usecases.user.LoginUseCase
import jaya.tech.exchange.application.usecases.user.LoginUseCaseImpl
import jaya.tech.exchange.infra.apiclient.ExchangeApiGatewayImpl
import jaya.tech.exchange.infra.database.UserRepositoryImpl
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