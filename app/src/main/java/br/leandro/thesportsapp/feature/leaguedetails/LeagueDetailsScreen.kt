package br.leandro.thesportsapp.feature.leaguedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Boy
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Girl
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.leandro.core.domain.model.League
import br.leandro.thesportsapp.R
import br.leandro.thesportsapp.ui.components.indicators.LoadingIndicator
import br.leandro.thesportsapp.ui.components.label.ColoredChip
import coil.compose.AsyncImage
import java.util.Locale

@Composable
fun LeagueDetailsScreen(
    uiState: LeagueDetailsUiState,
    modifier: Modifier = Modifier

) {
    when (uiState) {
        LeagueDetailsUiState.Loading -> LoadingIndicator(
            message = stringResource(R.string.loading_league_detail),
            modifier = Modifier
        )

        is LeagueDetailsUiState.Success -> {
            LeagueDetailsContent(league = uiState.league, modifier = Modifier)
        }

    }
}

@Composable
fun LeagueDetailsContent(league: League, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Card com gradiente e sombra
        Card(
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            if (league.image.isNotBlank()) {
                AsyncImage(
                    model = league.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        modifier = Modifier.size(96.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = league.league,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        if (league.leagueAlternate.isNotEmpty()) {
            Text(
                text = league.leagueAlternate,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Flag,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = league.country,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val statusText = if (league.complete) {
                stringResource(R.string.status_completed)
            } else {
                stringResource(R.string.status_in_progress)
            }

            ColoredChip(
                text = "${stringResource(R.string.league_status)} $statusText",
                color = MaterialTheme.colorScheme.secondary,
                icon = Icons.Default.EmojiEvents
            )

            val divisionText = if (league.isCup) {
                stringResource(R.string.cup)
            } else {
                "${league.division}ª ${stringResource(R.string.division)}"
            }

            ColoredChip(
                text = divisionText,
                color = MaterialTheme.colorScheme.secondary,
                icon = Icons.Default.SportsSoccer
            )

            if (league.tvRights.isNotEmpty()) {
                ColoredChip(
                    text = "${stringResource(R.string.league_tv_rights)} ${league.tvRights}",
                    color = MaterialTheme.colorScheme.secondary,
                    icon = Icons.Default.EmojiEvents
                )
            }

            if (league.gender.isNotEmpty()) {
                ColoredChip(
                    text = league.gender,
                    color = MaterialTheme.colorScheme.secondary,
                    icon = if (league.gender == "Male") Icons.Default.Boy else Icons.Default.Girl
                )
            }

            if (league.formedYear.isNotEmpty()) {
                ColoredChip(
                    text = "${stringResource(R.string.league_founded)} ${league.formedYear}",
                    color = MaterialTheme.colorScheme.secondary,
                    icon = Icons.Default.CalendarToday
                )
            }
        }


        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = leagueDescription(league),
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 22.sp
        )
    }
}




@Composable
fun leagueDescription(league: League): String {
    val locale = Locale.getDefault().language
    return if (!league.descriptionPT.isNullOrBlank() && locale.startsWith("pt")) {
        league.descriptionPT!!
    } else {
        league.descriptionEN
    }
}