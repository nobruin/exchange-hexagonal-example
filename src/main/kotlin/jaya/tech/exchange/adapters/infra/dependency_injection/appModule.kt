package jaya.tech.exchange.adapters.infra.dependency_injection

import jaya.tech.exchange.adapters.infra.external.ExchangeApiGatewayImpl
import jaya.tech.exchange.adapters.infra.authentication.JwtTokenProviderImpl
import jaya.tech.exchange.adapters.infra.database.UserRepositoryImpl
import jaya.tech.exchange.adapters.infra.external.ServiceCacheImpl
import jaya.tech.exchange.adapters.rest.controllers.ExchangeController
import jaya.tech.exchange.adapters.rest.controllers.UserController
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCaseImpl
import jaya.tech.exchange.application.usecases.user.CreateUserUseCaseIMpl
import jaya.tech.exchange.application.usecases.user.LoginUseCaseImpl
import jaya.tech.exchange.ports.input.authentication.JwtTokenProvider
import jaya.tech.exchange.ports.input.usecases.exchange.ConvertCurrencyUseCase
import jaya.tech.exchange.ports.input.usecases.user.CreateUserUseCase
import jaya.tech.exchange.ports.input.usecases.user.LoginUseCase
import jaya.tech.exchange.ports.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

var appModule = module {
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory<CreateUserUseCase> { CreateUserUseCaseIMpl(get()) }
    factory<LoginUseCase> { LoginUseCaseImpl(get()) }
    factory<ConvertCurrencyUseCase> { ConvertCurrencyUseCaseImpl(get(), get()) }
    factory<ExchangeGateway> { ExchangeApiGatewayImpl(get(), ServiceCacheImpl()) }
    factory<JwtTokenProvider> { JwtTokenProviderImpl() }
    factory { ExchangeController(get(), get()) }
    factory { UserController(get(), get(), get()) }
}

var databaseModule = module {
    single {
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver"
        )
    }
}
