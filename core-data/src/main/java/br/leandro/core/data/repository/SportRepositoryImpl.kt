package br.leandro.core.data.repository

import br.leandro.core.data.datasource.SportsDataSource
import br.leandro.core.data.mapper.toAppError
import br.leandro.core.data.mapper.toDomain
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.Sport
import br.leandro.core.domain.repository.SportRepository
import br.leandro.core.network.error.NetworkError
import br.leandro.core.network.error.toNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SportRepositoryImpl(private val sportsDataSource: SportsDataSource) : SportRepository {

    override suspend fun getSports(): Flow<List<Sport>> =
        flow {
            val response = sportsDataSource
                .getSports()
            emit(response.sports?.map { it.toDomain() } ?: emptyList())
        }.catch {throwable ->
            throw throwable.toNetworkError().toAppError()

        }.flowOn(Dispatchers.IO)

}