package br.leandro.core.data.remote.sports

import br.leandro.core.network.model.dto.SportsDto
import br.leandro.core.network.model.dto.SportsResponseDto

interface SportsRemoteDataSource {
    suspend fun getSports() : List<SportsDto>
}