package br.leandro.core.data.remote.country

import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.model.dto.CountryDto

class CountriesRemoteDataSourceImpl(private val api : TheSportsDbApi) : CountriesRemoteDataSource {
    override suspend fun getCountries(): List<CountryDto> =
        api.getCountries().countries


}