package br.leandro.thesportsapp.feature.sportdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.leandro.core.domain.model.Sport
import org.koin.androidx.compose.koinViewModel

@Composable
fun SportDetailsRoute(
    sport : Sport,
    onSeeLeaguesClick : (Sport) -> Unit
) {
    val viewModel : SportDetailsViewModel = koinViewModel()
    viewModel.initSportDetails(sport)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is SportDetailsUiEvent.OnSeeLeaguesClicked -> onSeeLeaguesClick(event.sport)
            }
        }
    }

    SportDetailsScreen(
        uiState = uiState.value,
        onSeeLeaguesClick = viewModel::onSeeLeaguesClicked
    )
}


