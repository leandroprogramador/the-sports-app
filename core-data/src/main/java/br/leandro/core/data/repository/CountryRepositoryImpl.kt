package br.leandro.core.data.repository

import br.leandro.core.data.local.datasource.countries.CountriesLocalDataSource
import br.leandro.core.data.mapper.toAppError
import br.leandro.core.data.mapper.toDomain
import br.leandro.core.data.mapper.toEntity
import br.leandro.core.data.remote.country.CountriesRemoteDataSource
import br.leandro.core.domain.model.Country
import br.leandro.core.domain.repository.CountryRepository
import br.leandro.core.network.error.toNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class CountryRepositoryImpl(
    private val localDataSource: CountriesLocalDataSource,
    private val remoteDataSource: CountriesRemoteDataSource
) : CountryRepository {

    override suspend fun getCountries(): Flow<List<Country>> = flow {
        try {
            val remoteCountries = remoteDataSource.getCountries()
            localDataSource.saveCountries(remoteCountries.map { it.toDomain().toEntity() })

        } catch (t: Throwable) {
            if (!localDataSource.hasData()) throw t.toNetworkError().toAppError()
        }
        emitAll(localDataSource.getCountries().map { list -> list.map { it.toDomain() } })

    }.flowOn(Dispatchers.IO)
}