package br.leandro.thesportsapp.navigation

import androidx.navigation3.runtime.NavKey
import br.leandro.core.domain.model.Sport
import kotlinx.serialization.Serializable

sealed interface AppRoute : NavKey{

    @Serializable
    data object SportsList : AppRoute

    @Serializable
    data class SportsDetails(val sport : Sport) : AppRoute

    @Serializable
    data class CountryList(val sport : Sport) : AppRoute

    @Serializable
    data class LeaguesList(val sport : String, val country : String) : AppRoute
}

