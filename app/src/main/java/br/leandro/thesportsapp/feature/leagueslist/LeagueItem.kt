package br.leandro.thesportsapp.feature.leagueslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.leandro.core.domain.model.League
import br.leandro.thesportsapp.R
import coil.compose.AsyncImage

@Composable
fun LeagueItem(
    league: League,
    modifier: Modifier = Modifier,
    onLeagueClick: (League) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onLeagueClick(league) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (league.badge.isNotEmpty()) {
                AsyncImage(
                    model = league.badge,
                    contentDescription = league.league,
                    modifier = Modifier.size(24.dp) // imagem menor dentro do círculo
                )
            } else {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = league.league,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary // contraste com fundo
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = league.league,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (league.isCup) "Cup"
                else "${league.division}ª ${stringResource(R.string.division)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
