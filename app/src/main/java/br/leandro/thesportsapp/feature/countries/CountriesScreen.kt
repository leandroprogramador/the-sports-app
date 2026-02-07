package br.leandro.thesportsapp.feature.countries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.leandro.core.domain.model.Country
import br.leandro.thesportsapp.R
import br.leandro.thesportsapp.ui.components.indicators.ErrorIndicator
import br.leandro.thesportsapp.ui.components.indicators.LoadingIndicator

@Composable
fun CountriesScreen(
    uiState: CountriesUiState,
    searchQuery : String,
    onSearchQueryChanged : (String) -> Unit,
    onCountryClick: (Country) -> Unit
) {
    when (uiState) {
        is CountriesUiState.Success -> {
            val filteredLeagues = remember(searchQuery, uiState.countries) {
                if (searchQuery.isBlank()) {
                    uiState.countries
                } else {
                    uiState.countries.filter {
                        it.name.contains(searchQuery, ignoreCase = true)
                    }
                }
            }
            CountriesList(
                countries = filteredLeagues,
                onCountryClick = onCountryClick,
                searchQuery = searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                modifier = Modifier
            )
        }
        is CountriesUiState.Error -> ErrorIndicator(
            appError = uiState.error,
            modifier = Modifier
        )
        is CountriesUiState.Loading -> LoadingIndicator(
            message = stringResource(R.string.loading_countries),
            modifier = Modifier
        )


    }
}