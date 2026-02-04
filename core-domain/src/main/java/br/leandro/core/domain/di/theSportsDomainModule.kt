package br.leandro.core.domain.di

import br.leandro.core.domain.usecase.GetSportsUseCase
import org.koin.dsl.module

val theSportsDomainModule = module{
    factory { GetSportsUseCase(get()) }
}