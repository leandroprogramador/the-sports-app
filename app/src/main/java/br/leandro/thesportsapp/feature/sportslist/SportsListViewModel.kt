package br.leandro.thesportsapp.feature.sportslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.leandro.core.domain.model.Sport
import br.leandro.core.domain.usecase.GetSportsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SportsListViewModel(private val getSportsUseCase: GetSportsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<SportsListUiState>(SportsListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SportsListUiEvent?>()
    val events : SharedFlow<SportsListUiEvent?> = _events.asSharedFlow()

    init {
        getSports()
    }

    fun getSports() {
        viewModelScope.launch {
            getSportsUseCase().onStart {
                _uiState.value = SportsListUiState.Loading
            }.catch {
                _uiState.value = SportsListUiState.Error(it.message ?: "Ocorreu um erro ao buscar a lista de esportes")
            }.collect {
                _uiState.value = SportsListUiState.Success(it)
            }
        }
    }

    fun onSportClicked(sport: Sport) {
        viewModelScope.launch {
            _events.emit(SportsListUiEvent.OnSportClicked(sport))
        }
    }



}
