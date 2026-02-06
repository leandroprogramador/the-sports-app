package br.leandro.core.domain.usecase

import app.cash.turbine.test
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.Country
import br.leandro.core.domain.repository.CountryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCountriesUseCaseTest {
    private val repository: CountryRepository = mockk()
    private lateinit var getCountriesUseCase : GetCountriesUseCase
    private val mockCountries = listOf(
        Country(name = "Argentina", flag = ""),
        Country(name = "Brazil", flag = "")
    )


    @Before
    fun setup() {
        getCountriesUseCase = GetCountriesUseCase(repository)
    }

    @Test
    fun `invoke should call repository and return countries flow`() = runTest {
        coEvery { repository.getCountries() } returns flowOf(mockCountries)
        val result = getCountriesUseCase()
        result.test {
            val countriesList = awaitItem()
            assert(countriesList.size == mockCountries.size)
            assert(countriesList.first().name == mockCountries.first().name)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should return empty when repository is empty`() = runTest {
        coEvery { repository.getCountries() } returns flowOf(emptyList())
        val result = getCountriesUseCase()
        result.test {
            val countriesList = awaitItem()
            assert(countriesList.isEmpty())
            awaitComplete()

        }
    }

    @Test(expected = AppError.Unknown::class)
    fun `invoke should return error when repository throws exception`() = runTest {
        coEvery { repository.getCountries() } throws AppError.Unknown
        val result = getCountriesUseCase()
        result.test {
            val error = awaitError()
            assert(error is AppError.Unknown)
        }
    }


}