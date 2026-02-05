package br.leandro.core.data.datasource

import br.leandro.core.network.model.dto.SportsResponseDto

interface SportsDataSource {
    suspend fun getSports() : SportsResponseDto
}