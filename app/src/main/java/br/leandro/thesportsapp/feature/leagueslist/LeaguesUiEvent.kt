package br.leandro.thesportsapp.feature.leagueslist

import br.leandro.core.domain.model.League


interface LeaguesUiEvent {
    data class OnLeagueClicked(val league: League) : LeaguesUiEvent
}