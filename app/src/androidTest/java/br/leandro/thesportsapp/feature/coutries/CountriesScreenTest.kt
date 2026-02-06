package br.leandro.thesportsapp.feature.coutries

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.Country
import br.leandro.core.domain.usecase.GetCountriesUseCase
import br.leandro.thesportsapp.R
import br.leandro.thesportsapp.feature.countries.CountriesList
import br.leandro.thesportsapp.feature.countries.CountriesScreen
import br.leandro.thesportsapp.feature.countries.CountriesUiState
import br.leandro.thesportsapp.feature.countries.CountriesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class CountriesScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private  val mockList = listOf(
        Country(name = "Argentina", flag = ""),
        Country(name = "Brazil", flag = ""),
        Country(name = "Germany", flag = "")
    )

    @Test
    fun shouldDisplayLoadingState() {
        composeTestRule.setContent {
            CountriesScreen(
                uiState = CountriesUiState.Loading,
                "",
                {},
                {}
            )
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(br.leandro.thesportsapp.R.string.loading_countries)

        composeTestRule.onNodeWithText(expectedText, substring = true)
            .assertIsDisplayed()

    }

    @Test
    fun shouldDisplaySuccessState() {


        composeTestRule.setContent {
            CountriesScreen(
                uiState = CountriesUiState.Success(mockList),
                "",
                {},
                {}
            )
        }

        composeTestRule.onNodeWithText("Argentina").assertIsDisplayed()
        composeTestRule.onNodeWithText("Brazil").assertIsDisplayed()

    }

    @Test
    fun shouldDisplayErrorState() {
        composeTestRule.setContent {
            CountriesScreen(
                uiState = CountriesUiState.Error(AppError.NoConnection),
                "",
                {},
                {}
            )
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(R.string.error_no_connection)
        composeTestRule.onNodeWithText(expectedText, substring = true)
            .assertIsDisplayed()

    }

    @Test
    fun shouldInvokeOnCountryClick() {
        var clicked = false

        composeTestRule.setContent {
            CountriesScreen(
                uiState = CountriesUiState.Success(mockList),
                "",
                {},
                {clicked = true}
            )
        }
        composeTestRule.onNodeWithText("Argentina").performClick()
        assertTrue(clicked)


    }

    @Test
    fun shouldFilterCountriesByValidSearchQuery() {
        val useCase = mockk<GetCountriesUseCase>()
        coEvery { useCase() } returns flowOf(mockList )
        val viewModel = CountriesViewModel(useCase)

        composeTestRule.setContent {
            CountriesScreen (
                uiState = viewModel.uiState.collectAsState().value,
                onCountryClick = {},
                searchQuery = viewModel.searchQuery.collectAsState().value,
                onSearchQueryChanged = viewModel::onSearchQueryChanged
            )
        }

        composeTestRule.onNode(hasSetTextAction()).performTextInput("Bra")

        composeTestRule.onNodeWithText("Brazil").assertIsDisplayed()
        composeTestRule.onNodeWithText("Argentina").assertDoesNotExist()
        composeTestRule.onNodeWithText("Germany").assertDoesNotExist()
    }

    @Test
    fun shouldFilterCountriesByInvalidSearchQueryAndShowEmptyState() {
        val useCase = mockk<GetCountriesUseCase>()
        coEvery { useCase() } returns flowOf(mockList )
        val viewModel = CountriesViewModel(useCase)

        composeTestRule.setContent {
            CountriesScreen (
                uiState = viewModel.uiState.collectAsState().value,
                onCountryClick = {},
                searchQuery = viewModel.searchQuery.collectAsState().value,
                onSearchQueryChanged = viewModel::onSearchQueryChanged
            )
        }

        composeTestRule.onNode(hasSetTextAction()).performTextInput("Fra")
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.onNodeWithText("Brazil").assertDoesNotExist()
        composeTestRule.onNodeWithText("Argentina").assertDoesNotExist()
        composeTestRule.onNodeWithText("Germany").assertDoesNotExist()
        composeTestRule.onNodeWithText( context.getString(R.string.empty_countries)).assertIsDisplayed()
    }



}





