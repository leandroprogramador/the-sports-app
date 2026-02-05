package br.leandro.thesportsapp.feature.sportslist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.leandro.core.domain.model.Sport
import br.leandro.thesportsapp.ui.components.indicators.ErrorIndicator
import br.leandro.thesportsapp.ui.components.indicators.LoadingIndicator

@Composable
fun SportsListScreen(
    uiState: SportsListUiState,
    onSportClick: (Sport) -> Unit
) {
    when(uiState) {
        SportsListUiState.Loading -> LoadingIndicator(message = "Carregando esportes...", modifier = Modifier)
        is SportsListUiState.Success -> SportsListContent(
            sports = uiState.sports,
            onSportClick = onSportClick
        )
        is SportsListUiState.Error -> ErrorIndicator(message = uiState.message, modifier = Modifier)

    }


}