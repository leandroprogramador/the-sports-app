package br.leandro.thesportsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember


@Composable
fun rememberAppNavState(
    startDestination: AppRoute = AppRoute.SportsList
): MutableList<AppRoute> {
    return remember {
        mutableStateListOf(startDestination)
    }
}