package br.leandro.core.data.local.datasource.leagues

import br.leandro.core.data.local.dao.LeagueDao
import br.leandro.core.data.local.entity.LeagueEntity
import kotlinx.coroutines.flow.Flow

class LeaguesLocalDataSourceImpl(private val dao: LeagueDao) : LeaguesLocalDataSource {

    override suspend fun getLeagues(country: String, sport: String): Flow<List<LeagueEntity>> =
        dao.getLeagues(country, sport)


    override suspend fun saveLeagues(leagues: List<LeagueEntity>) {
        if (leagues.isEmpty()) return
        dao.clearLeagues(leagues.first().country, leagues.first().sport)
        dao.insetLeagues(leagues)

    }


    override suspend fun hasData(): Boolean = dao.count() > 0
}