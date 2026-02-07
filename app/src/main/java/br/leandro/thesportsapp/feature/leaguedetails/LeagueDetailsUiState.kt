package br.leandro.thesportsapp.feature.leaguedetails

import br.leandro.core.domain.model.League
import br.leandro.core.domain.model.Sport

sealed interface LeagueDetailsUiState {
    object Loading : LeagueDetailsUiState
    data class Success(val league: League) : LeagueDetailsUiState

}