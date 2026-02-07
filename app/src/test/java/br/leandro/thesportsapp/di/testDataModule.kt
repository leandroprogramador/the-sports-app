package br.leandro.thesportsapp.di

import androidx.room.Room
import br.leandro.core.data.local.database.SportsDatabase
import br.leandro.core.data.local.datasource.sports.SportsLocalDataSource
import br.leandro.core.data.local.datasource.sports.SportsLocalDataSourceImpl
import br.leandro.core.data.remote.sports.SportsRemoteDataSource
import br.leandro.core.data.remote.sports.SportsRemoteDataSourceImpl
import br.leandro.core.data.repository.SportRepositoryImpl
import br.leandro.core.domain.repository.SportRepository
import br.leandro.core.data.local.datasource.countries.CountriesLocalDataSource
import br.leandro.core.data.local.datasource.countries.CountriesLocalDataSourceImpl
import br.leandro.core.data.local.datasource.leagues.LeaguesLocalDataSource
import br.leandro.core.data.local.datasource.leagues.LeaguesLocalDataSourceImpl
import br.leandro.core.data.remote.country.CountriesRemoteDataSource
import br.leandro.core.data.remote.country.CountriesRemoteDataSourceImpl
import br.leandro.core.data.remote.league.LeaguesRemoteDataSource
import br.leandro.core.data.remote.league.LeaguesRemoteDataSourceImpl
import br.leandro.core.data.repository.CountryRepositoryImpl
import br.leandro.core.data.repository.LeagueRepositoryImpl
import br.leandro.core.domain.repository.CountryRepository
import br.leandro.core.domain.repository.LeagueRepository
import org.koin.dsl.module

val inMemoryDataBaseModule = module {
    single<SportsDatabase> {
        Room.inMemoryDatabaseBuilder(get(), SportsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}
val testSportDataModule = module {
    single { get<SportsDatabase>().sportDao() }
    single<SportsLocalDataSource> { SportsLocalDataSourceImpl(get()) }
    single<SportsRemoteDataSource> { SportsRemoteDataSourceImpl(get()) }
    single<SportRepository> { SportRepositoryImpl(get(), get()) }
}

val testCountryDataModule = module {
    single { get<SportsDatabase>().countryDao() }
    single<CountriesLocalDataSource> { CountriesLocalDataSourceImpl(get()) }
    single<CountriesRemoteDataSource> { CountriesRemoteDataSourceImpl(get()) }
    single<CountryRepository> { CountryRepositoryImpl(get(), get()) }
}

val testLeaguesDataModule = module {
    single { get<SportsDatabase>().leagueDao() }
    single<LeaguesLocalDataSource> { LeaguesLocalDataSourceImpl(get()) }
    single<LeaguesRemoteDataSource> { LeaguesRemoteDataSourceImpl(get()) }
    single<LeagueRepository> { LeagueRepositoryImpl(get(), get()) }
}
