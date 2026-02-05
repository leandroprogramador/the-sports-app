package br.leandro.core.data.di

import androidx.room.Room
import br.leandro.core.data.local.database.SportsDatabase
import br.leandro.core.data.local.datasource.sports.SportLocalDataSourceImpl
import br.leandro.core.data.local.datasource.sports.SportsLocalDataSource
import br.leandro.core.data.remote.sports.SportsRemoteDataSource
import br.leandro.core.data.remote.sports.SportsRemoteRemoteDataSourceImpl
import br.leandro.core.data.repository.SportRepositoryImpl
import br.leandro.core.domain.repository.SportRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreDataModule = module {
    single<SportsDatabase> { Room
        .databaseBuilder(androidContext(), SportsDatabase::class.java, "sports_db")
        .build()
    }

    single { get<SportsDatabase>().sportDao() }

    single<SportsLocalDataSource> { SportLocalDataSourceImpl(get()) }

    single<SportsRemoteDataSource> { SportsRemoteRemoteDataSourceImpl(get()) }

    single<SportRepository> {
        SportRepositoryImpl(get(), get())
    }

}