package br.leandro.thesportsapp.feature.sportslist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.leandro.core.domain.model.Sport
import br.leandro.thesportsapp.feature.countries.CountriesUiEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun SportsListRoute(
    onSportClick : (Sport) -> Unit,

    ) {
    val viewModel : SportsListViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is SportsListUiEvent.OnSportClicked -> onSportClick(event.sport)
            }
        }
    }

    SportsListScreen(
        uiState = uiState,
        onSportClick = viewModel::onSportClicked
    )
}




