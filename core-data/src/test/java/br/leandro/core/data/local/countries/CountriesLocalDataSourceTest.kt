package br.leandro.core.data.local.countries

import app.cash.turbine.test
import br.leandro.core.data.local.dao.CountryDao
import br.leandro.core.data.local.datasource.countries.CountriesLocalDataSourceImpl
import br.leandro.core.data.local.entity.CountryEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CountriesLocalDataSourceTest {
    private val dao: CountryDao = mockk(relaxed = true)
    private lateinit var dataSource: CountriesLocalDataSourceImpl
    private val entityCountriesList = listOf(
        CountryEntity(name = "Argentina", flagUrl = ""),
        CountryEntity(name = "Brazil", flagUrl = "")
    )

    @Before
    fun setup() {
        dataSource = CountriesLocalDataSourceImpl(dao)
    }

    @Test
    fun `when getCountries is called should return countries list`() = runTest {

        every { dao.getCountries() } returns flowOf(entityCountriesList)
        val result = dataSource.getCountries()
        result.test {
            assertEquals(entityCountriesList, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `has data should return true when dao has data`() = runTest {
        coEvery { dao.count() } returns 1
        val result = dataSource.hasData()
        assertEquals(true, result)
    }

    @Test
    fun `when insert is called and count is greater than zero then hasData returns true`() = runTest {
        coEvery { dao.insertCountries(countries = entityCountriesList) } just Runs
        coEvery { dao.count() } returns entityCountriesList.size

        dataSource.saveCountries(entityCountriesList)
        val result = dataSource.hasData()
        assertEquals(true, result)

        coVerify(exactly = 1) { dao.insertCountries(countries = entityCountriesList) }
        coVerify(exactly = 1) { dao.count() }
    }


}







