package br.leandro.thesportsapp.feature.countries

import br.leandro.core.domain.model.Country

sealed class CountriesUiEvent {
    data class OnCountryClicked(val country: Country) : CountriesUiEvent()
}