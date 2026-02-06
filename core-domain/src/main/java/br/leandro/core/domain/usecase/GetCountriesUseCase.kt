package br.leandro.core.domain.usecase

import br.leandro.core.domain.model.Country
import br.leandro.core.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow

class GetCountriesUseCase(private val repository: CountryRepository) {
    suspend operator fun invoke() : Flow<List<Country>> = repository.getCountries()
}


