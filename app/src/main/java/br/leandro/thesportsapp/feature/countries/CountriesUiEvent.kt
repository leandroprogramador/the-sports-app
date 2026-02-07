package br.leandro.thesportsapp.feature.countries

import br.leandro.core.domain.model.Country

sealed interface CountriesUiEvent {
    data class OnCountryClicked(val country: Country) : CountriesUiEvent
}