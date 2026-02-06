package br.leandro.thesportsapp.navigation

import androidx.activity.compose.BackHandler

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import br.leandro.thesportsapp.feature.sportdetails.SportDetailsRoute
import br.leandro.thesportsapp.feature.sportslist.SportsListRoute
import br.leandro.thesportsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val backStack = rememberAppNavState()
    val currentRoute = backStack.lastOrNull() ?: AppRoute.SportsList

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text =  when (currentRoute) {
                            is AppRoute.SportsList -> stringResource(R.string.sports)
                            is AppRoute.SportsDetails -> stringResource(R.string.details)
                        },
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    if(currentRoute !is AppRoute.SportsList) {
                        IconButton(onClick = { backStack.removeLastOrNull() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        AppNavDisplay(backStack, currentRoute, innerPadding)
    }



}


@Composable
fun AppNavDisplay(backStack: MutableList<AppRoute>, currentRoute : AppRoute, innerPadding: PaddingValues) {
    Box(modifier = Modifier.padding(innerPadding)) {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { navKey ->
                when (navKey) {
                    is AppRoute.SportsList -> {
                        NavEntry(navKey) {
                            SportsListRoute(
                                onSportClick = { sport ->
                                    backStack.add(AppRoute.SportsDetails(sport))
                                }
                            )
                        }
                    }

                    is AppRoute.SportsDetails -> {
                        NavEntry(navKey) {
                            SportDetailsRoute(sport = navKey.sport)
                        }
                    }
                }
            })
    }
    BackHandler(enabled = currentRoute !is AppRoute.SportsList) {
        backStack.removeLastOrNull()
    }
}
