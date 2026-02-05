package br.leandro.core.data.remote.sports

import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.model.dto.SportsDto
import br.leandro.core.network.model.dto.SportsResponseDto

class SportsRemoteRemoteDataSourceImpl(private val api: TheSportsDbApi) : SportsRemoteDataSource {
    override suspend fun getSports(): List<SportsDto> =
        try {
            api.getSports().sports.orEmpty()
        } catch (t: Throwable) {
            throw t
        }

}