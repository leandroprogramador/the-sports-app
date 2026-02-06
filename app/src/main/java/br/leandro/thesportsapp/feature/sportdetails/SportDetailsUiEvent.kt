package br.leandro.thesportsapp.feature.sportdetails

import br.leandro.core.domain.model.Sport

sealed interface SportDetailsUiEvent {
    data class OnSeeLeaguesClicked(val sport: Sport) : SportDetailsUiEvent
}