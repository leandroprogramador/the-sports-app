package br.leandro.core.network.api

import br.leandro.core.network.model.dto.CountriesResponseDto
import br.leandro.core.network.model.dto.LeagueResponseDto
import br.leandro.core.network.model.dto.SportsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportsDbApi {
    @GET("all_sports.php")
    suspend fun getSports() : SportsResponseDto

    @GET("all_countries.php")
    suspend fun getCountries() : CountriesResponseDto

    @GET("search_all_leagues.php")
    suspend fun getLeagues(
        @Query("c") country: String,
        @Query("s") sport: String
    ) : LeagueResponseDto
}


