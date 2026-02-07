package br.leandro.thesportsapp.feature.leaguedetails

import androidx.lifecycle.ViewModel
import br.leandro.core.domain.model.League

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LeagueDetailsViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow<LeagueDetailsUiState>(LeagueDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun initLeagueDetails(league: League) {
        _uiState.value = LeagueDetailsUiState.Success(league)
    }

}