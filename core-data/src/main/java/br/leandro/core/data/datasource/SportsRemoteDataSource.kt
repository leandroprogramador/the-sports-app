package br.leandro.core.data.datasource

import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.error.toNetworkError
import br.leandro.core.network.model.dto.SportsResponseDto

class SportsRemoteDataSource(private val api: TheSportsDbApi) : SportsDataSource {
    override suspend fun getSports(): SportsResponseDto =
        try {
            api.getSports()
        } catch (t: Throwable) {
            throw t
        }

}