package br.leandro.thesportsapp.feature.sportslist

import br.leandro.core.domain.model.Sport

sealed interface SportsListUiState {
    object Loading : SportsListUiState
    data class Success(val sports: List<Sport>) : SportsListUiState
    data class Error(val message: String) : SportsListUiState
}

