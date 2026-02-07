package br.leandro.thesportsapp.feature.leagueslist

import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.League

sealed interface LeaguesUiState {
    object Loading : LeaguesUiState
    data class Success(val leagues: List<League>) : LeaguesUiState
    data class Error(val error: AppError) : LeaguesUiState
}
