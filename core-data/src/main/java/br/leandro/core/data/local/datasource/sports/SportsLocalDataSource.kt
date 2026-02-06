package br.leandro.core.data.local.datasource.sports

import br.leandro.core.data.local.entity.SportEntity
import kotlinx.coroutines.flow.Flow

interface SportsLocalDataSource {
    suspend fun getSports() : Flow<List<SportEntity>>
    suspend fun saveSports(sports: List<SportEntity>)
    suspend fun hasData() : Boolean
}


