package br.leandro.thesportsapp.feature.sportdetails

import br.leandro.core.domain.model.Sport

sealed class SportDetailsUiState {
    object Loading : SportDetailsUiState()
    data class Success(val sport: Sport) : SportDetailsUiState()
}