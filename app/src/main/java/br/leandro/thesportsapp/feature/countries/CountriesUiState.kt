package br.leandro.thesportsapp.feature.countries

import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.Country

sealed class CountriesUiState {
    object Loading : CountriesUiState()
    data class Success(val countries: List<Country>) : CountriesUiState()
    data class Error(val error : AppError) : CountriesUiState()
}



