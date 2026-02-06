package br.leandro.core.domain.di

import br.leandro.core.domain.usecase.GetCountriesUseCase
import br.leandro.core.domain.usecase.GetSportsUseCase
import org.koin.dsl.module

val coreDomainModule = module{
    factory { GetSportsUseCase(get()) }
    factory { GetCountriesUseCase(get()) }
}