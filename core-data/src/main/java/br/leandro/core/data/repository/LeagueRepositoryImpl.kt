package br.leandro.core.data.repository

import br.leandro.core.data.local.datasource.leagues.LeaguesLocalDataSource
import br.leandro.core.data.mapper.toAppError
import br.leandro.core.data.mapper.toDomain
import br.leandro.core.data.mapper.toEntity
import br.leandro.core.data.remote.league.LeaguesRemoteDataSource
import br.leandro.core.domain.model.League
import br.leandro.core.domain.repository.LeagueRepository
import br.leandro.core.network.error.toNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LeagueRepositoryImpl(
    private val localDataSource: LeaguesLocalDataSource,
    private val remoteDataSource: LeaguesRemoteDataSource
) :
    LeagueRepository {
    override suspend fun getLeagues(country: String, sport: String): Flow<List<League>> = flow {
        try {
            val remoteLeagues = remoteDataSource.getLeagues(country, sport)
            localDataSource.saveLeagues(remoteLeagues.map { it.toDomain().toEntity() })
        } catch (t: Throwable) {
            if (!localDataSource.hasData()) throw t.toNetworkError().toAppError()
        }
        emitAll(localDataSource.getLeagues(country, sport).map { list ->
            list.map { it.toDomain() }
        })
    }
}
