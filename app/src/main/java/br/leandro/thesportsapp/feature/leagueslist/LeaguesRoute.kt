package br.leandro.thesportsapp.feature.leagueslist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.leandro.core.domain.model.League
import org.koin.androidx.compose.koinViewModel

@Composable
fun LeaguesRoute(
    sport: String,
    country: String,
    onLeagueClick: (league: League) -> Unit,
) {
    val viewModel: LeaguesViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    LaunchedEffect(sport, country) { viewModel.getLeagues(sport, country) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LeaguesUiEvent.OnLeagueClicked -> onLeagueClick(event.league)
            }
        }
    }

    LeagueScreen(
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        searchQuery = searchQuery,
        onLeagueClick = viewModel::onLeagueClicked
    )



}
