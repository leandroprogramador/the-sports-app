package br.leandro.core.domain.usecase

import br.leandro.core.domain.model.Sport
import br.leandro.core.domain.repository.SportRepository
import kotlinx.coroutines.flow.Flow

class GetSportsUseCase(private val sportRepository: SportRepository) {
    suspend operator fun invoke(): Flow<List<Sport>> = sportRepository.getSports()
}