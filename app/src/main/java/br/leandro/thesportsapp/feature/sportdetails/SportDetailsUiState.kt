package br.leandro.thesportsapp.feature.sportdetails

import br.leandro.core.domain.model.Sport

sealed interface SportDetailsUiState {
    object Loading : SportDetailsUiState
    data class Success(val sport: Sport) : SportDetailsUiState
}