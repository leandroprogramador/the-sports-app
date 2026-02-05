package br.leandro.core.data.di

import br.leandro.core.data.datasource.SportsDataSource
import br.leandro.core.data.datasource.SportsRemoteDataSource
import br.leandro.core.data.repository.SportRepositoryImpl
import br.leandro.core.domain.repository.SportRepository
import org.koin.dsl.module

val theSportsDbDataModule = module {
    single<SportsDataSource> { SportsRemoteDataSource(get()) }

    single<SportRepository> {
        SportRepositoryImpl(get())
    }

}