package br.leandro.core.data.remote.country

import br.leandro.core.network.model.dto.CountryDto

interface CountriesRemoteDataSource {
    suspend fun getCountries(): List<CountryDto>
}