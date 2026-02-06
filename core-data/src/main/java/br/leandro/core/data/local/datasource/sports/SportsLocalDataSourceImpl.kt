package br.leandro.core.data.local.datasource.sports

import br.leandro.core.data.local.dao.SportDao
import br.leandro.core.data.local.entity.SportEntity
import kotlinx.coroutines.flow.Flow

class SportsLocalDataSourceImpl(private val dao : SportDao) : SportsLocalDataSource {
    override suspend fun getSports(): Flow<List<SportEntity>> = dao.getSports()

    override suspend fun saveSports(sports: List<SportEntity>) {
        dao.clear()
        dao.insertSports(sports)
    }

    override suspend fun hasData(): Boolean = dao.count() > 0

}