package br.leandro.thesportsapp.feature.countries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.leandro.core.domain.model.Country
import br.leandro.core.domain.model.Sport
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CountriesRoute(
    sport : Sport,
    onCountryClick : (countryName : String, sport : Sport) -> Unit,
) {
    val viewModel : CountriesViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CountriesUiEvent.OnCountryClicked -> onCountryClick(event.country.name, sport)
            }
        }
    }

    CountriesScreen(
        uiState = uiState,
        onCountryClick = viewModel::onCountryClicked,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        searchQuery = searchQuery

    )
}