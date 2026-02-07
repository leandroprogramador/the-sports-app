package br.leandro.thesportsapp.feature.leagueslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.League
import br.leandro.core.domain.usecase.GetLeaguesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LeaguesViewModel(
    private val getLeaguesUseCase: GetLeaguesUseCase
) : ViewModel() {

    private val _params =
        MutableStateFlow<Pair<String, String>?>(null)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _events = MutableSharedFlow<LeaguesUiEvent>()
    val events = _events.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<LeaguesUiState> =
        _params
            .filterNotNull()
            .flatMapLatest { (country, sport) ->
                getLeaguesUseCase(country, sport)
                    .map<List<League>, LeaguesUiState> { leagues ->
                        LeaguesUiState.Success(leagues)
                    }
                    .onStart {
                        emit(LeaguesUiState.Loading)
                    }
                    .catch { error ->
                        emit(
                            LeaguesUiState.Error(
                                error as? AppError ?: AppError.Unknown
                            )
                        )
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LeaguesUiState.Loading
            )

    fun getLeagues(sport: String, country: String) {
        _searchQuery.value = ""
        _params.value = country to sport
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onLeagueClicked(league: League) {
        viewModelScope.launch {
            _events.emit(LeaguesUiEvent.OnLeagueClicked(league))
        }
    }
}


