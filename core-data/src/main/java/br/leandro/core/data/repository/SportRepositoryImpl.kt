package br.leandro.core.data.repository

import br.leandro.core.data.datasource.SportsDataSource
import br.leandro.core.data.datasource.SportsRemoteDataSource
import br.leandro.core.data.mapper.toDomain
import br.leandro.core.domain.model.Sport
import br.leandro.core.domain.repository.SportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SportRepositoryImpl(private val sportsDataSource: SportsDataSource) : SportRepository {

    override suspend fun getSports(): Flow<List<Sport>> =
        flow {
            val response = sportsDataSource
                .getSports()
            emit(response.sports?.map { it.toDomain() } ?: emptyList())
        }.flowOn(Dispatchers.IO)

}