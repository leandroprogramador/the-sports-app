package br.leandro.core.data.local.datasource.countries

import br.leandro.core.data.local.entity.CountryEntity
import kotlinx.coroutines.flow.Flow

interface CountriesLocalDataSource {
    fun getCountries() : Flow<List<CountryEntity>>
    suspend fun saveCountries(countries : List<CountryEntity>)
    suspend fun hasData() : Boolean

}