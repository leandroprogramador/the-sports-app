package br.leandro.thesportsapp.feature.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.Country
import br.leandro.core.domain.usecase.GetCountriesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CountriesViewModel(private val getCountriesUseCase: GetCountriesUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _events = MutableSharedFlow<CountriesUiEvent>()
    val events: SharedFlow<CountriesUiEvent> = _events.asSharedFlow()




    init {
        getCountries()
    }

    fun getCountries() {
        viewModelScope.launch {
            getCountriesUseCase().onStart {
                _uiState.value = CountriesUiState.Loading
            }.catch {error ->
                _uiState.value = CountriesUiState.Error(error as? AppError ?: AppError.Unknown)
            }.collect { countries ->
                _uiState.value = CountriesUiState.Success(countries)
            }
        }
    }

    fun onSearchQueryChanged(query : String) {
        _searchQuery.value = query
    }

    fun onCountryClicked(country: Country) {
        viewModelScope.launch {
            _events.emit(CountriesUiEvent.OnCountryClicked(country))
        }

    }

    fun resetSearch() {
        _searchQuery.value = ""

    }


}