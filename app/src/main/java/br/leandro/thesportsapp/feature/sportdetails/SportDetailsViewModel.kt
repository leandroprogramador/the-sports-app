package br.leandro.thesportsapp.feature.sportdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.leandro.core.domain.model.Sport
import br.leandro.thesportsapp.feature.sportslist.SportsListUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SportDetailsViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow<SportDetailsUiState>(SportDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SportDetailsUiEvent>()
    val events : SharedFlow<SportDetailsUiEvent> = _events.asSharedFlow()

    fun initSportDetails(sport: Sport) {
        _uiState.value = SportDetailsUiState.Success(sport)
    }

    fun onSeeLeaguesClicked(sport: Sport) {
        viewModelScope.launch {
            _events.emit(SportDetailsUiEvent.OnSeeLeaguesClicked(sport))
        }
    }


}