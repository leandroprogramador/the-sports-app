package br.leandro.thesportsapp.feature.sportdetails

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.leandro.core.domain.model.Sport
import org.koin.androidx.compose.koinViewModel

@Composable
fun SportDetailsRoute(sport : Sport) {
    val viewModel : SportDetailsViewModel = koinViewModel()
    viewModel.initSportDetails(sport)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    SportDetailsScreen(uiState = uiState.value)
}


