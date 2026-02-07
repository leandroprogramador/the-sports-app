package br.leandro.thesportsapp.feature.sportdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.leandro.core.domain.model.Sport
import br.leandro.thesportsapp.ui.components.indicators.LoadingIndicator
import coil.compose.AsyncImage
import br.leandro.thesportsapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Composable
fun SportDetailsScreen(
    uiState: SportDetailsUiState,
    onSeeLeaguesClick: (Sport) -> Unit
) {
    when (uiState) {
        SportDetailsUiState.Loading -> LoadingIndicator(
            message = stringResource(R.string.loading_sports_details),
            modifier = Modifier
        )

        is SportDetailsUiState.Success -> {
            Scaffold(
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = { onSeeLeaguesClick(uiState.sport) },
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Filled.List,
                                contentDescription = null,
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(R.string.see_leagues)
                            )
                        })
                }

            ) { innerPadding ->
                SportDetailsContent(
                    sport = uiState.sport,
                    modifier = Modifier.padding(innerPadding)
                )

            }
        }
    }
}

@Composable
fun SportDetailsContent(sport: Sport, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        AsyncImage(
            model = sport.image,
            contentDescription = sport.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = sport.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp)

        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            modifier = Modifier
                .width(80.dp)
                .padding(horizontal = 16.dp),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = sport.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}