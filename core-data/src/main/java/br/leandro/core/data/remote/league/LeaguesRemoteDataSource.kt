package br.leandro.core.data.remote.league

import br.leandro.core.network.model.dto.LeagueDto

interface LeaguesRemoteDataSource {
    suspend fun getLeagues(country: String, sport: String): List<LeagueDto>
}