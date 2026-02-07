package br.leandro.core.data.di

import androidx.room.Room
import br.leandro.core.data.local.database.SportsDatabase
import br.leandro.core.data.local.datasource.countries.CountriesLocalDataSource
import br.leandro.core.data.local.datasource.countries.CountriesLocalDataSourceImpl
import br.leandro.core.data.local.datasource.leagues.LeaguesLocalDataSource
import br.leandro.core.data.local.datasource.leagues.LeaguesLocalDataSourceImpl
import br.leandro.core.data.local.datasource.sports.SportsLocalDataSourceImpl
import br.leandro.core.data.local.datasource.sports.SportsLocalDataSource
import br.leandro.core.data.remote.country.CountriesRemoteDataSource
import br.leandro.core.data.remote.country.CountriesRemoteDataSourceImpl
import br.leandro.core.data.remote.league.LeaguesRemoteDataSource
import br.leandro.core.data.remote.league.LeaguesRemoteDataSourceImpl
import br.leandro.core.data.remote.sports.SportsRemoteDataSource
import br.leandro.core.data.remote.sports.SportsRemoteDataSourceImpl
import br.leandro.core.data.repository.CountryRepositoryImpl
import br.leandro.core.data.repository.LeagueRepositoryImpl
import br.leandro.core.data.repository.SportRepositoryImpl
import br.leandro.core.domain.repository.CountryRepository
import br.leandro.core.domain.repository.LeagueRepository
import br.leandro.core.domain.repository.SportRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreDataModule = module {
    single<SportsDatabase> { Room
        .databaseBuilder(androidContext(), SportsDatabase::class.java, "sports_db")
        .build()
    }

    single { get<SportsDatabase>().sportDao() }
    single { get<SportsDatabase>().countryDao() }
    single { get<SportsDatabase>().leagueDao() }

    single<SportsLocalDataSource> { SportsLocalDataSourceImpl(get()) }
    single<SportsRemoteDataSource> { SportsRemoteDataSourceImpl(get()) }

    single<CountriesLocalDataSource> { CountriesLocalDataSourceImpl(get()) }
    single<CountriesRemoteDataSource> { CountriesRemoteDataSourceImpl(get()) }

    single<LeaguesLocalDataSource> { LeaguesLocalDataSourceImpl(get()) }
    single<LeaguesRemoteDataSource> { LeaguesRemoteDataSourceImpl(get()) }


    single<SportRepository> {
        SportRepositoryImpl(get(), get())
    }

    single<CountryRepository> {
        CountryRepositoryImpl(get(), get())
    }

    single<LeagueRepository> {
        LeagueRepositoryImpl(get(), get())
    }




}