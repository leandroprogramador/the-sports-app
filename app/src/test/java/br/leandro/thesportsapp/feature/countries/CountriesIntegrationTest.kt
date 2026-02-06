@file:OptIn(ExperimentalCoroutinesApi::class)

package br.leandro.thesportsapp.feature.countries

import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import br.leandro.core.data.local.database.SportsDatabase
import br.leandro.core.data.local.entity.CountryEntity
import br.leandro.core.data.remote.country.CountriesRemoteDataSource
import br.leandro.core.domain.di.coreDomainModule
import br.leandro.core.domain.model.AppError
import br.leandro.core.network.model.dto.CountriesResponseDto
import br.leandro.core.network.model.dto.CountryDto
import br.leandro.thesportsapp.di.appModule
import br.leandro.thesportsapp.di.inMemoryDataBaseModule
import br.leandro.thesportsapp.di.testCountryDataModule
import br.leandro.thesportsapp.util.NetworkProvider.createRetrofitServer
import br.leandro.thesportsapp.util.NetworkProvider.getJsonString
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertFails
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class CountriesIntegrationTest : KoinTest {
    private lateinit var server: MockWebServer
    private val testDispatcher = UnconfinedTestDispatcher()
    private val viewModel: CountriesViewModel by inject()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        server = MockWebServer()

        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(
                module {
                    single {
                        createRetrofitServer(server)
                    }
                },
                inMemoryDataBaseModule,
                testCountryDataModule,
                coreDomainModule,
                appModule
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        server.shutdown()
        Dispatchers.resetMain()
    }

    @Test
    fun `offline first flow emits local first then remote`() = runTest(testDispatcher) {
        val dao = get<SportsDatabase>().countryDao()
        val localCountry = CountryEntity(
            name = "Argentina",
            flagUrl = "url_icon"
        )
        dao.insertCountries(listOf(localCountry))

        val json = getJsonString("country_response.json")
        assertTrue(json.isNotEmpty(), "JSON não encontrado ou vazio")
        server.enqueue(MockResponse().setResponseCode(200).setBody(json))

        advanceUntilIdle()

        viewModel.uiState.test {
            val loading = awaitItem()
            println("DEBUG: Loading $loading")
            assertTrue(loading is CountriesUiState.Loading)


            val success = awaitItem()
            println("DEBUG: Success $success")

            assertTrue(success is CountriesUiState.Success)
            assertEquals(3, success.countries.size)
            assertTrue(success.countries.any { it.name == "Andorra" })

            cancelAndIgnoreRemainingEvents()
        }


        dao.getCountries().test {
            val countries = awaitItem()
            println("DEBUG: DAO retornou -> $countries")

            assertEquals(3, countries.size)
            assertTrue(countries.any { it.name == "Andorra" })
        }

    }

    @Test
    fun `offline first flow return local data when remote fails`() = runTest {
        val dao = get<SportsDatabase>().countryDao()
        val countries = listOf(
            CountryEntity(
                name = "Argentina",
                flagUrl = "url_icon"
            ),
            CountryEntity(
                name = "Brazil",
                flagUrl = "url_icon"
            )
        )
        dao.insertCountries(countries)
        server.enqueue(MockResponse().setResponseCode(500))
        advanceUntilIdle()

        viewModel.uiState.test {
            val loading = awaitItem()
            println("DEBUG: Loading $loading")
            assertTrue(loading is CountriesUiState.Loading)

            val success = awaitItem()
            println("DEBUG: Success $success")
            assertTrue(success is CountriesUiState.Success)
            assertEquals(2, success.countries.size)

            cancelAndIgnoreRemainingEvents()

        }
    }

    @Test
    fun `offline first flows emits error when api fails and no local data`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500))
        advanceUntilIdle()
        viewModel.uiState.test {
            val loading = awaitItem()
            println("DEBUG: Loading $loading")
            assertTrue(loading is CountriesUiState.Loading)

            val error = awaitItem()
            println("DEBUG: Error $error")
            assertTrue(error is CountriesUiState.Error)
            assertTrue(error.error is AppError.ServiceUnavailable)

            cancelAndIgnoreRemainingEvents()

        }

    }


}