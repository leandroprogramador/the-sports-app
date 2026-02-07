package br.leandro.thesportsapp.feature.leaguedetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.leandro.core.domain.model.League
import org.koin.androidx.compose.koinViewModel

@Composable
fun LeagueDetailsRoute(league: League) {
    val viewModel : LeagueDetailsViewModel = koinViewModel()
    viewModel.initLeagueDetails(league)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LeagueDetailsScreen(uiState.value, modifier = Modifier)
}