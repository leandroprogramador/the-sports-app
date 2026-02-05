package br.leandro.core.domain.repository

import br.leandro.core.domain.model.Sport
import kotlinx.coroutines.flow.Flow

interface SportRepository {
    suspend fun getSports() : Flow<List<Sport>>
}