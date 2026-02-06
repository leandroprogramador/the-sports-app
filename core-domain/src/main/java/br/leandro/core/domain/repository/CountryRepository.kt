package br.leandro.core.domain.repository

import br.leandro.core.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun getCountries() : Flow<List<Country>>

}