package br.leandro.thesportsapp.feature.sportdetails

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import br.leandro.core.domain.model.Sport
import org.junit.Rule
import org.junit.Test

class SportDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldShowSportContent() {
        val sport = Sport(id = "1", name = "Soccer", image = "", icon = "", description = "Soccer is the most popular sport in the world")
        val uiState = SportDetailsUiState.Success(sport)

        composeTestRule.setContent {
            SportDetailsScreen(uiState) {}
        }

        composeTestRule.onNodeWithText("Soccer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Soccer is the most popular sport in the world").assertIsDisplayed()

    }

    @Test
    fun shouldShowLoadingIndicator() {
        val uiState = SportDetailsUiState.Loading
        composeTestRule.setContent {
            SportDetailsScreen(uiState){}
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(br.leandro.thesportsapp.R.string.loading_sports_details)
        composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()

    }
}