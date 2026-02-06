package br.leandro.core.network.api

import br.leandro.core.network.model.dto.CountriesResponseDto
import br.leandro.core.network.model.dto.SportsResponseDto
import retrofit2.http.GET

interface TheSportsDbApi {
    @GET("all_sports.php")
    suspend fun getSports() : SportsResponseDto

    @GET("all_countries.php")
    suspend fun getCountries() : CountriesResponseDto
}


