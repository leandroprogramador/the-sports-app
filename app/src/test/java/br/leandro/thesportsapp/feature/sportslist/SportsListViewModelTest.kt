package br.leandro.thesportsapp.feature.sportslist

import app.cash.turbine.test
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.Sport
import br.leandro.core.domain.usecase.GetSportsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SportsListViewModelTest {
    private val getSportsUseCase : GetSportsUseCase = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockSports = listOf(
        Sport( id = "1",  name = "Soccer", description = "", icon = "", image = ""),
        Sport( id = "2",  name = "Basketball", description = "", icon = "", image = ""),
    )

    private lateinit var viewModelTest: SportsListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getSports should emit Success state when getSportsUseCase returns sports list`() = runTest {

        coEvery { getSportsUseCase() } returns flowOf(mockSports)
        viewModelTest = SportsListViewModel(getSportsUseCase)

        viewModelTest.uiState.test {
            val emission = awaitItem()
            assert(emission is SportsListUiState.Success)
            assertEquals(mockSports, (emission as SportsListUiState.Success).sports)

        }
    }

    @Test
    fun `getSports should emit Success state when getSportsUseCase returns empty sports list`() = runTest {
        val mockSports = emptyList<Sport>()

        coEvery { getSportsUseCase() } returns flowOf(mockSports)
        viewModelTest = SportsListViewModel(getSportsUseCase)

        viewModelTest.uiState.test {
            val emission = awaitItem()
            assert(emission is SportsListUiState.Success)
            assertEquals(mockSports, (emission as SportsListUiState.Success).sports)

        }
    }

    @Test
    fun `getSports should emit Error state when getSportsUseCase throws an exception`() = runTest {

        coEvery { getSportsUseCase() } returns flow { throw AppError.Unknown }
        viewModelTest = SportsListViewModel(getSportsUseCase)
        viewModelTest.uiState.test {
            val emission = awaitItem()
            assert(emission is SportsListUiState.Error)
        }
    }

    @Test
    fun `when onSportClicked is called, it should emit OnSportClicked event`() = runTest {
        val sport = mockSports.first()
        coEvery { getSportsUseCase() } returns flowOf(listOf(sport))
        viewModelTest = SportsListViewModel(getSportsUseCase)

        viewModelTest.events.test {
            viewModelTest.onSportClicked(sport)
            val event = awaitItem()
            assert(event is SportsListUiEvent.OnSportClicked)
            assertEquals(sport, (event as SportsListUiEvent.OnSportClicked).sport)


        }

    }
}