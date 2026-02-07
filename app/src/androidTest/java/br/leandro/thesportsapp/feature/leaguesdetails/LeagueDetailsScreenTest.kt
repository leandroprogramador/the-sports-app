package br.leandro.thesportsapp.feature.leaguesdetails

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import br.leandro.thesportsapp.R
import br.leandro.thesportsapp.feature.leaguedetails.LeagueDetailsScreen
import br.leandro.thesportsapp.feature.leaguedetails.LeagueDetailsUiState
import br.leandro.thesportsapp.feature.leagues.LeagueMock.leagueList
import org.junit.Rule
import org.junit.Test

class LeagueDetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldShowLeagueCompletedContent() {
        val league = leagueList().first()
        val uiState = LeagueDetailsUiState.Success(league)
        composeTestRule.setContent {
            LeagueDetailsScreen(uiState)
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val foundedLabel = context.getString(R.string.league_founded)
        val leagueStatusLabel = context.getString(R.string.league_status)
        val leagueStatus = context.getString(R.string.status_completed)
        composeTestRule.onNodeWithText(league.league).assertIsDisplayed()
        composeTestRule.onNodeWithText(league.country).assertIsDisplayed()
        composeTestRule.onNodeWithText("$foundedLabel ${league.formedYear}").assertIsDisplayed()
        composeTestRule.onNodeWithText("$leagueStatusLabel $leagueStatus").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Male").assertIsDisplayed()
    }

    @Test
    fun shouldShowLeagueInProgressContent() {
        val league = leagueList()[1]
        val uiState = LeagueDetailsUiState.Success(league)
        composeTestRule.setContent {
            LeagueDetailsScreen(uiState)
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val foundedLabel = context.getString(R.string.league_founded)
        val leagueStatusLabel = context.getString(R.string.league_status)
        val leagueStatus = context.getString(R.string.status_in_progress)
        composeTestRule.onNodeWithText(league.league).assertIsDisplayed()
        composeTestRule.onNodeWithText(league.country).assertIsDisplayed()
        composeTestRule.onNodeWithText("$foundedLabel ${league.formedYear}").assertIsDisplayed()
        composeTestRule.onNodeWithText("$leagueStatusLabel $leagueStatus").assertIsDisplayed()
    }

    @Test
    fun shouldShowLoadingIndicator() {
        val uiState = LeagueDetailsUiState.Loading
        composeTestRule.setContent {
            LeagueDetailsScreen(uiState)
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(br.leandro.thesportsapp.R.string.loading_league_detail)
        composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
    }
}