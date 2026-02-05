package br.leandro.thesportsapp.feature.sportdetails

import androidx.lifecycle.ViewModel
import br.leandro.core.domain.model.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SportDetailsViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow<SportDetailsUiState>(SportDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun initSportDetails(sport: Sport) {
        _uiState.value = SportDetailsUiState.Success(sport)
    }


}