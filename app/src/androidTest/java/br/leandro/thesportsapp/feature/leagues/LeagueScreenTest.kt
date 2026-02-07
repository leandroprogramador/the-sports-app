package br.leandro.thesportsapp.feature.leagues

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import br.leandro.core.domain.model.AppError
import br.leandro.thesportsapp.R
import br.leandro.thesportsapp.feature.leagues.LeagueMock.leagueList
import br.leandro.thesportsapp.feature.leagueslist.LeagueScreen
import br.leandro.thesportsapp.feature.leagueslist.LeaguesUiState
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class LeagueScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayLoadingState() {
        composeTestRule.setContent {
            LeagueScreen(
                uiState = LeaguesUiState.Loading,
                searchQuery = "",
                onSearchQueryChanged = {},
                onLeagueClick = {}

            )
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(R.string.loading_leagues)

        composeTestRule.onNodeWithText(expectedText, substring = true)
            .assertIsDisplayed()



    }

    @Test
    fun shouldDisplaySuccessState() {
        composeTestRule.setContent {
            LeagueScreen(
                uiState = LeaguesUiState.Success(leagueList()),
                {},
                "",
                {}
            )
        }

        composeTestRule.onNodeWithText("Campeonato Brasileiro").assertIsDisplayed()
        composeTestRule.onNodeWithText("Campeonato Brasileiro Serie B").assertIsDisplayed()

    }

    @Test
    fun shouldDisplayErrorState() {
        composeTestRule.setContent {
            LeagueScreen(
                uiState = LeaguesUiState.Error(AppError.NoConnection),
                {},
                "",
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
            LeagueScreen(
                uiState = LeaguesUiState.Success(leagueList()),
                {},
                "",
                {clicked = true}
            )
        }
        composeTestRule.onNodeWithText("Campeonato Brasileiro").performClick()
        assertTrue(clicked)


    }

    @Test
    fun shouldFilterCountriesByValidSearchQuery() {

        composeTestRule.setContent {
            var query by remember { mutableStateOf("") }

            LeagueScreen(
                uiState = LeaguesUiState.Success(leagueList()),
                {query = it},
                query,
                {}
            )
        }

        composeTestRule
            .onNode(hasSetTextAction())
            .performTextInput("Serie B")

        composeTestRule.onNodeWithText("Campeonato Brasileiro Serie B").assertIsDisplayed()
        composeTestRule.onNodeWithText("Campeonato Brasileiro").assertDoesNotExist()
    }


    @Test
    fun shouldShowEmptyStateWhenSearchReturnsNoResults() {
        composeTestRule.setContent {
            var query by remember { mutableStateOf("") }

            LeagueScreen(
                uiState = LeaguesUiState.Success(leagueList()),
                {query = it},
                query,
                {}
            )
        }

        composeTestRule
            .onNode(hasSetTextAction())
            .performTextInput("Copa do Brasil")

        composeTestRule.onNodeWithText("Campeonato Brasileiro Serie B").assertDoesNotExist()
        composeTestRule.onNodeWithText("Campeonato Brasileiro").assertDoesNotExist()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule
            .onNodeWithText(context.getString(R.string.empty_leagues))
            .assertIsDisplayed()
    }



}