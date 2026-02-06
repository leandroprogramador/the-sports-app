package br.leandro.core.data.local.datasource.countries

import br.leandro.core.data.local.dao.CountryDao
import br.leandro.core.data.local.entity.CountryEntity
import kotlinx.coroutines.flow.Flow

class CountriesLocalDataSourceImpl(private val dao : CountryDao) : CountriesLocalDataSource {
    override fun getCountries(): Flow<List<CountryEntity>> = dao.getCountries()


    override suspend fun saveCountries(countries: List<CountryEntity>) {
        dao.insertCountries(countries)
    }

    override suspend fun hasData(): Boolean = dao.count() > 0
}