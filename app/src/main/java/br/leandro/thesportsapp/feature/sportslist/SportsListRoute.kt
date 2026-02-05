package br.leandro.thesportsapp.feature.sportslist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.leandro.core.domain.model.Sport
import org.koin.androidx.compose.koinViewModel

@Composable
fun SportsListRoute(
    onSportClick : (Sport) -> Unit,

    ) {
    val viewModel : SportsListViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SportsListScreen(
        uiState = uiState,
        onSportClick = onSportClick
    )
}




