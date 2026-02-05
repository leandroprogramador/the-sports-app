package br.leandro.thesportsapp.feature.sportslist

import br.leandro.core.domain.model.Sport

sealed interface SportsListUiEvent {
    data class OnSportClicked(val sport: Sport) : SportsListUiEvent

}