package br.leandro.thesportsapp.feature.leagueslist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.leandro.core.domain.model.League
import br.leandro.thesportsapp.R
import br.leandro.thesportsapp.ui.components.indicators.ErrorIndicator
import br.leandro.thesportsapp.ui.components.indicators.LoadingIndicator

@Composable
fun LeagueScreen(
    uiState: LeaguesUiState,
    onSearchQueryChanged: (query: String) -> Unit,
    searchQuery: String,
    onLeagueClick: (league: League) -> Unit,
) {
    when (uiState) {
        is LeaguesUiState.Error -> ErrorIndicator(
            appError = uiState.error,
            modifier = Modifier
        )
        is LeaguesUiState.Loading -> LoadingIndicator(
            message = stringResource(R.string.loading_leagues),
            modifier = Modifier
        )
        is LeaguesUiState.Success -> {
            val filteredLeagues = remember(searchQuery, uiState.leagues) {
                if (searchQuery.isBlank()) {
                    uiState.leagues
                } else {
                    uiState.leagues.filter {
                        it.league.contains(searchQuery, ignoreCase = true) ||
                                it.leagueAlternate.contains(searchQuery, ignoreCase = true)
                    }
                }
            }

            LeaguesList(
                leagues = filteredLeagues,
                onLeagueClick = onLeagueClick,
                searchQuery = searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
            )
        }
    }
}
