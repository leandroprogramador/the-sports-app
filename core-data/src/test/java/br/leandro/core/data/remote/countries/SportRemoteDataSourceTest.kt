package br.leandro.core.data.remote.countries

import br.leandro.core.data.remote.country.CountriesRemoteDataSourceImpl
import br.leandro.core.data.remote.sports.SportsRemoteDataSourceImpl
import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.model.dto.CountriesResponseDto
import br.leandro.core.network.model.dto.CountryDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class SportRemoteDataSourceTest {
    private val api: TheSportsDbApi = mockk()
    private lateinit var dataSource: CountriesRemoteDataSourceImpl
    private val dtoCountriesResponse = CountriesResponseDto(
        listOf(
            CountryDto(name_en = "Argentina", flag_url_32 = "flag_url"),
            CountryDto(name_en = "Brazil", flag_url_32 = "flag_url")
        )
    )


    @Before
    fun setup() {
        dataSource = CountriesRemoteDataSourceImpl(api)
    }

    @Test
    fun `when api answer success should return countries list`() = runTest {
        coEvery { api.getCountries() } returns dtoCountriesResponse
        val result = dataSource.getCountries()
        assert(result.size == dtoCountriesResponse.countries.size)
        assert(result.first().name_en == dtoCountriesResponse.countries.first().name_en)

    }

    @Test(expected = IOException::class)
    fun `when api fails should return exception`() = runTest {
        coEvery { api.getCountries() } throws IOException()
        dataSource.getCountries()

    }


}