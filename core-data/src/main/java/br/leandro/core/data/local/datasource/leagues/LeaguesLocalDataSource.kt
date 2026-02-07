package br.leandro.core.data.local.datasource.leagues

import br.leandro.core.data.local.entity.LeagueEntity
import kotlinx.coroutines.flow.Flow

interface LeaguesLocalDataSource {
    suspend fun getLeagues(country : String, sport: String) : Flow<List<LeagueEntity>>
    suspend fun saveLeagues(leagues : List<LeagueEntity>)
    suspend fun hasData() : Boolean


}