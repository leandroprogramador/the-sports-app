package br.leandro.core.domain.repository

import br.leandro.core.domain.model.League
import kotlinx.coroutines.flow.Flow

interface LeagueRepository {
    suspend fun getLeagues(country : String, sport : String): Flow<List<League>>
}

