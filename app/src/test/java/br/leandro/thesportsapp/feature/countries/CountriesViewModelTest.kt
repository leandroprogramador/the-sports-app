package br.leandro.thesportsapp.feature.countries

import app.cash.turbine.test
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.Country
import br.leandro.core.domain.usecase.GetCountriesUseCase
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
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)

class CountriesViewModelTest {
    private val getCountriesUseCase: GetCountriesUseCase = mockk()
    private lateinit var viewModelTest: CountriesViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockCountries = listOf(
        Country(name = "Argentina", flag = ""),
        Country(name = "Brazil", flag = ""),
        Country(name = "France", flag = ""),
        Country(name = "Germany", flag = "")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCountries should emit Success state when getCountriesUseCase returns countries list`() =
        runTest {
            coEvery { getCountriesUseCase() } returns flowOf(mockCountries)
            viewModelTest = CountriesViewModel(getCountriesUseCase)
            viewModelTest.uiState.test {
                val emission = awaitItem()
                assert(emission is CountriesUiState.Success)
                assertEquals(mockCountries, (emission as CountriesUiState.Success).countries)
            }
        }

    @Test
    fun `getCountries should emit Success state when getCountriesUseCase returns empty countries list`() =
        runTest {
            val mockCountries = emptyList<Country>()
            coEvery { getCountriesUseCase() } returns flowOf(mockCountries)
            viewModelTest = CountriesViewModel(getCountriesUseCase)
            viewModelTest.uiState.test {
                val emission = awaitItem()
                assert(emission is CountriesUiState.Success)
                assertEquals(mockCountries, (emission as CountriesUiState.Success).countries)
            }
        }

    @Test
    fun `getCountries should emit Error state when getCountriesUseCase throws an exception`() =
        runTest {
            coEvery { getCountriesUseCase() } returns flow { throw AppError.ServiceUnavailable }
            viewModelTest = CountriesViewModel(getCountriesUseCase)
            viewModelTest.uiState.test {
                val emission = awaitItem()
                assert(emission is CountriesUiState.Error)
            }
        }

    @Test
    fun `when onCountryClicked is called, it should emit OnCountryClicked event`() = runTest {
        val country = mockCountries.first()
        coEvery { getCountriesUseCase() } returns flowOf(listOf(country))
        viewModelTest = CountriesViewModel(getCountriesUseCase)
        viewModelTest.events.test {
            viewModelTest.onCountryClicked(country)
            val event = awaitItem()
            assert(event is CountriesUiEvent.OnCountryClicked)
            assertEquals(country, (event as CountriesUiEvent.OnCountryClicked).country)
        }

    }

    @Test
    fun `onSearchQuery with empty blank should return all countries`() = runTest {
        coEvery { getCountriesUseCase() } returns flowOf(mockCountries)
        viewModelTest = CountriesViewModel(getCountriesUseCase)

        viewModelTest.uiState.test {
            val initial = awaitItem()
            assert(initial is CountriesUiState.Success)
            assertEquals(mockCountries, (initial as CountriesUiState.Success).countries)
            cancelAndIgnoreRemainingEvents()
        }
        viewModelTest.onSearchQueryChanged("")

        viewModelTest.uiState.test {
            val afterSearch = expectMostRecentItem()
            assert(afterSearch is CountriesUiState.Success)
            assertEquals(mockCountries, (afterSearch as CountriesUiState.Success).countries)
        }

    }

    @Test
    fun `onSearchQuery with a valid query should return filtered countries`() = runTest {
        coEvery { getCountriesUseCase() } returns flowOf(mockCountries)
        viewModelTest = CountriesViewModel(getCountriesUseCase)

        viewModelTest.uiState.test {
            val initial = awaitItem()
            assert(initial is CountriesUiState.Success)
            assertEquals(mockCountries, (initial as CountriesUiState.Success).countries)
            cancelAndIgnoreRemainingEvents()
        }
        viewModelTest.onSearchQueryChanged("Arg")

        viewModelTest.uiState.test {
            val afterSearch = expectMostRecentItem()
            assert(afterSearch is CountriesUiState.Success)
            assertEquals(1, (afterSearch as CountriesUiState.Success).countries.size)
            assertEquals("Argentina", afterSearch.countries.first().name)
        }
    }

    @Test
    fun `onSearchQuery with a invalid query should return empty list`() = runTest {
        coEvery { getCountriesUseCase() } returns flowOf(mockCountries)
        viewModelTest = CountriesViewModel(getCountriesUseCase)
        viewModelTest.uiState.test {
            val initial = awaitItem()
            assert(initial is CountriesUiState.Success)
            assertEquals(mockCountries, (initial as CountriesUiState.Success).countries)
            cancelAndIgnoreRemainingEvents()
        }

        viewModelTest.onSearchQueryChanged("Port")
        viewModelTest.uiState.test {
            val afterSearch = expectMostRecentItem()
            assert(afterSearch is CountriesUiState.Success)
            assertEquals(0, (afterSearch as CountriesUiState.Success).countries.size)
        }

    }


}




