package br.leandro.thesportsapp.feature.sportslist

import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import br.leandro.core.data.local.database.SportsDatabase
import br.leandro.core.data.local.entity.SportEntity
import br.leandro.core.domain.di.coreDomainModule
import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.thesportsapp.di.appModule
import br.leandro.thesportsapp.di.testDataModule
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
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SportsListIntegrationTest : KoinTest {

    private lateinit var server: MockWebServer
    private val testDispatcher = UnconfinedTestDispatcher()
    private val viewModel: SportsListViewModel by inject()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        server = MockWebServer()

        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(
                module {
                    single {
                        Retrofit.Builder()
                            .baseUrl(server.url("/"))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(TheSportsDbApi::class.java)
                    }
                },
                testDataModule,
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
        val dao = get<SportsDatabase>().sportDao()
        val localSport = SportEntity(
            id = "103",
            name = "Basketball",
            description = "Description",
            iconUrl = "url_icon",
            imageUrl = "url_thumb"
        )
        dao.insertSports(listOf(localSport))

        val json = getJsonString()
        assertTrue(json.isNotEmpty(), "JSON não encontrado ou vazio")

        server.enqueue(MockResponse().setResponseCode(200).setBody(json))

        viewModel.getSports()

        advanceUntilIdle()

        viewModel.uiState.test {
            val loading = awaitItem()
            assertTrue(loading is SportsListUiState.Loading)

            val success = awaitItem()
            assertTrue(success is SportsListUiState.Success)
            assertEquals(2, success.sports.size)
            assertTrue(success.sports.any { it.name == "Basketball" })
            assertTrue(success.sports.any { it.name == "Soccer" })

            cancelAndIgnoreRemainingEvents()
        }


        dao.getSports().test {
            val sports = awaitItem()
            println("DEBUG: DAO retornou -> $sports")
            assertEquals(2, sports.size)
            assertTrue(sports.any { it.name == "Basketball" })
            assertTrue(sports.any { it.name == "Soccer" })
        }

    }

    @Test
    fun `offline first flow emits local when api fails but local data exists`() = runTest(testDispatcher) {
        val dao = get<SportsDatabase>().sportDao()
        val localSport = SportEntity(
            id = "201",
            name = "Volleyball",
            description = "Local description",
            iconUrl = "local_icon",
            imageUrl = "local_thumb"
        )
        dao.insertSports(listOf(localSport))

        server.enqueue(MockResponse().setResponseCode(500))

        viewModel.getSports()
        advanceUntilIdle()

        viewModel.uiState.test {
            val loading = awaitItem()
            assertTrue(loading is SportsListUiState.Loading)
            val success = awaitItem()
            assertTrue(success is SportsListUiState.Success)
            assertEquals(1, success.sports.size)
            assertEquals("Volleyball", success.sports.first().name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `offline first flow emits error when api fails and no local data`() = runTest(testDispatcher) {

        server.enqueue(MockResponse().setResponseCode(500))

        viewModel.getSports()
        advanceUntilIdle()

        viewModel.uiState.test {
            val loading = awaitItem()
            assertTrue(loading is SportsListUiState.Loading)
            val error = awaitItem()
            assertTrue(error is SportsListUiState.Error)

            cancelAndIgnoreRemainingEvents()
        }
    }


    private fun getJsonString(): String {
        return javaClass.classLoader
            ?.getResourceAsStream("sports_response.json")
            ?.bufferedReader()
            ?.use { it.readText() } ?: ""
    }
}





