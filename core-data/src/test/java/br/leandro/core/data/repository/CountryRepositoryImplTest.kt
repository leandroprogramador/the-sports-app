package br.leandro.core.data.repository

import app.cash.turbine.test
import br.leandro.core.data.local.datasource.countries.CountriesLocalDataSource
import br.leandro.core.data.local.entity.CountryEntity
import br.leandro.core.data.mapper.toDomain
import br.leandro.core.data.mapper.toEntity
import br.leandro.core.data.remote.country.CountriesRemoteDataSource
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.repository.CountryRepository
import br.leandro.core.network.model.dto.CountryDto
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class CountryRepositoryImplTest {
    private val remoteDataSource: CountriesRemoteDataSource = mockk()
    private val localDataSource: CountriesLocalDataSource = mockk()
    private lateinit var repository : CountryRepository

    @Before
    fun setup() {
        repository = CountryRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `when api answer success should update local list and return countries list`() = runTest {
        coEvery { localDataSource.getCountries() } returns flowOf(countriesEntity())
        coEvery { remoteDataSource.getCountries() } returns countriesDto()
        coEvery { localDataSource.saveCountries(countriesDto().map { it.toDomain().toEntity() }) } just Runs
        coEvery { localDataSource.hasData() } returns true

        val result = repository.getCountries()
        result.test {
            assertEquals(countriesEntity().first().name, awaitItem().first().name)
            awaitComplete()
        }

    }

    @Test
    fun `when api fails and has local should return local countries list`() = runTest {
        coEvery { localDataSource.getCountries() } returns flowOf(countriesEntity())
        coEvery { remoteDataSource.getCountries() } throws AppError.ServiceUnavailable
        coEvery { localDataSource.hasData() } returns true

        val result = repository.getCountries()
        result.test {
            assertEquals(countriesEntity().first().name, awaitItem().first().name)
            awaitComplete()
        }

    }

    @Test(expected = AppError.NoConnection::class)
    fun `when api fails and has no local should throw exception`() = runTest {
        coEvery { localDataSource.getCountries() } returns flowOf(emptyList())
        coEvery { localDataSource.hasData() } returns false
        coEvery { remoteDataSource.getCountries() } throws UnknownHostException()
        repository.getCountries().collect()

    }


    private fun countriesDto() = listOf(
        CountryDto(name_en = "Argentina", flag_url_32 = "flag_url"),
        CountryDto(name_en = "Brazil", flag_url_32 = "flag_url")
    )

    private fun countriesEntity() = listOf(
        CountryEntity(name = "France", flagUrl = "flag_url"),
        CountryEntity(name = "Germany", flagUrl = "flag_url")
    )
}