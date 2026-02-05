package br.leandro.thesportsapp.feature.sportslist

import android.util.Log
import app.cash.turbine.test
import br.leandro.core.data.di.theSportsDbDataModule
import br.leandro.core.domain.di.theSportsDomainModule
import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.di.theSportsDbNetworkModule
import br.leandro.thesportsapp.di.appModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class SportsListIntegrationTest : KoinTest {
    private lateinit var server: MockWebServer
    private val viewModel: SportsListViewModel by inject()
    private val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        server = MockWebServer()

        startKoin {
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
                theSportsDbDataModule,
                theSportsDomainModule,
                appModule
            )

        }
    }

    @After
    fun tearDown() {
        stopKoin()
        server.shutdown()
    }

    @Test
    fun `complete flow should return sports list`() = runTest {
        val jsonBody = getJsonString()
        server.enqueue(MockResponse().setBody(jsonBody).setResponseCode(200))
        viewModel.getSports()
        viewModel.uiState.test {
            assertTrue(awaitItem() is SportsListUiState.Loading)
            val successState = awaitItem()
            assertTrue(successState is SportsListUiState.Success)
            assertEquals(1, successState.sports.size)
            assertEquals("Soccer", successState.sports[0].name)

            cancelAndConsumeRemainingEvents()

        }
    }

    @Test
    fun `should handle error when api returns error`() = runTest {
        server.enqueue(MockResponse().setResponseCode(404))
        viewModel.getSports()
        viewModel.uiState.test {
            assertTrue(awaitItem() is SportsListUiState.Loading)
            val errorState = awaitItem()
            assertTrue(errorState is SportsListUiState.Error)

            cancelAndIgnoreRemainingEvents()
        }


    }

    private fun getJsonString(): String {
        return try {
            val inputStream = javaClass.classLoader?.getResourceAsStream("sports_response.json")
            inputStream?.bufferedReader().use { it?.readText() } ?: ""
        } catch (e: Exception) {
            ""
        }
    }
}