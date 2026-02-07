package br.leandro.core.data.remote.league

import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.model.dto.LeagueDto

class LeaguesRemoteDataSourceImpl(private val api: TheSportsDbApi) : LeaguesRemoteDataSource {
    override suspend fun getLeagues(
        country: String,
        sport: String
    ): List<LeagueDto>  =
        try {
            api.getLeagues(country, sport).countries
        } catch (t: Throwable) {
            throw t

        }

}