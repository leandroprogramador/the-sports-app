package br.leandro.thesportsapp.di

import androidx.room.Room
import br.leandro.core.data.local.database.SportsDatabase
import br.leandro.core.data.local.datasource.sports.SportsLocalDataSource
import br.leandro.core.data.local.datasource.sports.SportsLocalDataSourceImpl
import br.leandro.core.data.remote.sports.SportsRemoteDataSource
import br.leandro.core.data.remote.sports.SportsRemoteDataSourceImpl
import br.leandro.core.data.repository.SportRepositoryImpl
import br.leandro.core.domain.repository.SportRepository
import org.koin.dsl.module

val testDataModule = module {
    single<SportsDatabase> {
        Room.inMemoryDatabaseBuilder(get(), SportsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    single { get<SportsDatabase>().sportDao() }
    single<SportsLocalDataSource> { SportsLocalDataSourceImpl(get()) }
    single<SportsRemoteDataSource> { SportsRemoteDataSourceImpl(get()) }
    single<SportRepository> { SportRepositoryImpl(get(), get()) }
}
