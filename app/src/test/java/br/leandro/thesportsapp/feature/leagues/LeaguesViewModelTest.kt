package br.leandro.thesportsapp.feature.leagues

import app.cash.turbine.test
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.League
import br.leandro.core.domain.usecase.GetLeaguesUseCase
import br.leandro.thesportsapp.feature.leagueslist.LeaguesUiEvent
import br.leandro.thesportsapp.feature.leagueslist.LeaguesUiState
import br.leandro.thesportsapp.feature.leagueslist.LeaguesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LeaguesViewModelTest {
    private val getLeaguesUseCase: GetLeaguesUseCase = mockk()
    private lateinit var viewModelTest: LeaguesViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getLeagues should update uiState with success state`() = runTest {
        coEvery { getLeaguesUseCase(country = "Brazil", sport = "Soccer") } returns flowOf(
            mockLeagues
        )
        viewModelTest = LeaguesViewModel(getLeaguesUseCase)
        viewModelTest.getLeagues(country = "Brazil", sport = "Soccer")
        viewModelTest.uiState.test {
            val emission = awaitItem()
            assert(emission is LeaguesUiState.Success)
            assert((emission as LeaguesUiState.Success).leagues == mockLeagues)
        }

    }

    @Test
    fun `getLeagues should update uiState with empty list`() = runTest {
        coEvery {
            getLeaguesUseCase(
                country = "Brazil",
                sport = "Soccer"
            )
        } returns flowOf(emptyList())
        viewModelTest = LeaguesViewModel(getLeaguesUseCase)
        viewModelTest.getLeagues(country = "Brazil", sport = "Soccer")
        viewModelTest.uiState.test {
            val emission = awaitItem()
            assert(emission is LeaguesUiState.Success)
            assert((emission as LeaguesUiState.Success).leagues.isEmpty())
        }


    }

    @Test
    fun `getLeagues should update uiState with error state`() = runTest {
        coEvery {
            getLeaguesUseCase(
                country = "Brazil",
                sport = "Soccer"
            )
        } returns flow { throw AppError.ServiceUnavailable }
        viewModelTest = LeaguesViewModel(getLeaguesUseCase)
        viewModelTest.getLeagues(country = "Brazil", sport = "Soccer")
        viewModelTest.uiState.test {
            val emission = awaitItem()
            assert(emission is LeaguesUiState.Error)
        }

    }

    @Test
    fun `when onLeagueClicked is called, it should emit OnLeagueClicked event`() = runTest {
        val league = mockLeagues.first()
        coEvery { getLeaguesUseCase(country = "Brazil", sport = "Soccer") } returns flowOf(
            mockLeagues
        )
        viewModelTest = LeaguesViewModel(getLeaguesUseCase)

        viewModelTest.events.test {
            viewModelTest.onLeagueClicked(league)
            val event = awaitItem()
            assert(event is LeaguesUiEvent.OnLeagueClicked)
            assert((event as LeaguesUiEvent.OnLeagueClicked).league == league)
        }

    }

    @Test
    fun `onSearchQueryChanged should update searchQuery state`() = runTest {
        viewModelTest = LeaguesViewModel(getLeaguesUseCase)

        viewModelTest.searchQuery.test {
            assert(awaitItem() == "")
            viewModelTest.onSearchQueryChanged("Bundesli")
            assert(awaitItem() == "Bundesli")
        }
    }





}


private val mockLeagues = listOf(
    League(
        idLeague = "1234",
        division = 1,
        formedYear = "1959",
        badge = "https://upload.wikimedia.org/wikipedia/pt/0/0e/Campeonato_Brasileiro_Série_A_logo.png",
        complete = true,
        country = "Brazil",
        currentSeason = "2026",
        descriptionEN = "The Campeonato Brasileiro Série A is the top tier of professional football in Brazil.",
        descriptionPT = "O Campeonato Brasileiro Série A é a principal divisão do futebol profissional no Brasil.",
        image = "",
        gender = "Male",
        league = "Campeonato Brasileiro",
        leagueAlternate = "Brasileirão",
        sport = "Soccer",
        tvRights = "Globo, CazéTV",
        isCup = false
    )
)


