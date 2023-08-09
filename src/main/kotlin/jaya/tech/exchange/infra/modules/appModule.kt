package jaya.tech.exchange.infra.modules

import jaya.tech.exchange.adapters.rest.controllers.ExchangeController
import jaya.tech.exchange.adapters.rest.controllers.UserController
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCase
import jaya.tech.exchange.application.usecases.exchange.ConvertCurrencyUseCaseImpl
import jaya.tech.exchange.application.usecases.user.CreateUserUseCase
import jaya.tech.exchange.application.usecases.user.CreateUserUseCaseIMpl
import jaya.tech.exchange.application.usecases.user.LoginUseCase
import jaya.tech.exchange.application.usecases.user.LoginUseCaseImpl
import jaya.tech.exchange.infra.apiclient.ExchangeApiGatewayImpl
import jaya.tech.exchange.infra.authentication.JwtTokenProviderImpl
import jaya.tech.exchange.infra.database.UserRepositoryImpl
import jaya.tech.exchange.ports.output.authentication.JwtTokenProvider
import jaya.tech.exchange.ports.output.external.exchangeapi.ExchangeGateway
import jaya.tech.exchange.ports.output.persistence.repositories.UserRepository
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

var appModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<ExchangeGateway> { ExchangeApiGatewayImpl(get()) }
    single<CreateUserUseCase> { CreateUserUseCaseIMpl(get()) }
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<ConvertCurrencyUseCase> { ConvertCurrencyUseCaseImpl(get(), get()) }
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
