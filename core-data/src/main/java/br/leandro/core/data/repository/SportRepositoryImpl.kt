package br.leandro.core.data.repository

import br.leandro.core.data.local.datasource.sports.SportsLocalDataSource
import br.leandro.core.data.remote.sports.SportsRemoteDataSource
import br.leandro.core.data.mapper.toAppError
import br.leandro.core.data.mapper.toDomain
import br.leandro.core.data.mapper.toEntity
import br.leandro.core.domain.model.Sport
import br.leandro.core.domain.repository.SportRepository
import br.leandro.core.network.error.toNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SportRepositoryImpl(private val remoteDataSource: SportsRemoteDataSource, private val local: SportsLocalDataSource) : SportRepository {

    override suspend fun getSports(): Flow<List<Sport>> =
        flow {
            try {
                val remoteSports = remoteDataSource.getSports()
                local.saveSports(
                    remoteSports.map { it.toDomain().toEntity() }
                )
            } catch (t: Throwable) {
                if (!local.hasData()) {
                    throw t.toNetworkError().toAppError()
                }
            }

            emitAll(
                local.getSports()
                    .map { list -> list.map { it.toDomain() } }
            )
        }.flowOn(Dispatchers.IO)

}