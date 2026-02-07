package br.leandro.thesportsapp.feature.leagues

import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import br.leandro.core.data.local.database.SportsDatabase
import br.leandro.core.data.local.entity.LeagueEntity
import br.leandro.core.domain.di.coreDomainModule
import br.leandro.core.domain.model.AppError
import br.leandro.thesportsapp.di.appModule
import br.leandro.thesportsapp.di.inMemoryDataBaseModule
import br.leandro.thesportsapp.di.testLeaguesDataModule
import br.leandro.thesportsapp.feature.countries.CountriesUiState
import br.leandro.thesportsapp.feature.leagueslist.LeaguesUiState
import br.leandro.thesportsapp.feature.leagueslist.LeaguesViewModel
import br.leandro.thesportsapp.util.NetworkProvider.createRetrofitServer
import br.leandro.thesportsapp.util.NetworkProvider.getJsonString
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
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertTrue


@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)

class LeaguesIntegrationTest : KoinTest{
    private lateinit var server: MockWebServer
    private val testDispatcher = UnconfinedTestDispatcher()
    private val viewModel: LeaguesViewModel by inject()

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
                testLeaguesDataModule,
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
    fun `offline first flow emit local first then remote`() = runTest(testDispatcher) {
        val dao = get<SportsDatabase>().leagueDao()
        dao.insertLeagues(leaguesEntity())

        val json = getJsonString("leagues_response.json")
        assertTrue(json.isNotEmpty(), "JSON não encontrado ou vazio")
        server.enqueue(MockResponse().setResponseCode(200).setBody(json))
        advanceUntilIdle()
        viewModel.getLeagues(country = "Brazil", sport = "Soccer")

        viewModel.uiState.test {
            val loading = awaitItem()
            println("DEBUG: Loading $loading")
            assertTrue(loading is LeaguesUiState.Loading)


            val success = awaitItem()
            println("DEBUG: Success $success")

            assertTrue(success is LeaguesUiState.Success)
            assertEquals(2, success.leagues.size)
            assertTrue(success.leagues.any { it.league == "Brazil Brasileiro Women" })

            cancelAndIgnoreRemainingEvents()
        }


        dao.getLeagues(country = "Brazil", sport = "Soccer").test {
            val leagues = awaitItem()
            println("DEBUG: DAO retornou -> $leagues")

            assertEquals(2, leagues.size)
            assertTrue(leagues.any { it.league == "Brazil Brasileiro Women" })
        }


    }

    @Test
    fun `offline first flow local data when remote fails`() = runTest {
        val dao = get<SportsDatabase>().leagueDao()
        dao.insertLeagues(leaguesEntity())
        server.enqueue(MockResponse().setResponseCode(500))
        advanceUntilIdle()
        viewModel.getLeagues(country = "Brazil", sport = "Soccer")
        viewModel.uiState.test {
            val loading = awaitItem()
            println("DEBUG: Loading $loading")
            assertTrue(loading is LeaguesUiState.Loading)
            val success = awaitItem()
            println("DEBUG: Success $success")
            assertTrue(success is LeaguesUiState.Success)
            assertEquals(1, success.leagues.size)
            cancelAndIgnoreRemainingEvents()

        }

    }

    @Test
    fun `offline first flows emits error when api fails and no local data`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500))
        advanceUntilIdle()
        viewModel.getLeagues(country = "Brazil", sport = "Soccer")
        viewModel.uiState.test {
            val loading = awaitItem()
            println("DEBUG: Loading $loading")
            assertTrue(loading is LeaguesUiState.Loading)

            val error = awaitItem()
            println("DEBUG: Error $error")
            assertTrue(error is LeaguesUiState.Error)
            assertTrue(error.error is AppError.ServiceUnavailable)
            cancelAndIgnoreRemainingEvents()
        }

    }


    private fun leaguesEntity() = listOf(
        LeagueEntity(
            idLeague = "1234",
            division = "1",
            formedYear = "1959",
            badge = "https://upload.wikimedia.org/wikipedia/pt/0/0e/Campeonato_Brasileiro_Série_A_logo.png",
            complete = "Campeonato Brasileiro Série A",
            country = "Brazil",
            currentSeason = "2026",
            descriptionEN = "The Campeonato Brasileiro Série A is the top tier of professional football in Brazil.",
            descriptionPT = "O Campeonato Brasileiro Série A é a principal divisão do futebol profissional no Brasil.",
            image = "",
            gender = "Male",
            league = "Campeonato Brasileiro",
            leagueAlternate = "Brasileirão",
            sport = "Soccer",
            tvRights = "Globo, CazéTV"
        )

    )




}