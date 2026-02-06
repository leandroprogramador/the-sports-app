package br.leandro.thesportsapp.feature.sportslist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.Sport
import br.leandro.thesportsapp.test.R
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class SportsListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayLoadingState() {
        composeTestRule.setContent {
            SportsListScreen(
                uiState = SportsListUiState.Loading,
                onSportClick = {}
            )
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(br.leandro.thesportsapp.R.string.loading_sports)

        composeTestRule.onNodeWithText(expectedText, substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun shouldDisplaySuccessState() {
        val mockList = listOf(
            Sport(id = "1", name = "Soccer", image = "", icon = "", description = ""),
            Sport(id = "2", name = "Basketball", image = "", icon = "", description = "")
        )

        composeTestRule.setContent {
            SportsListScreen(
                uiState = SportsListUiState.Success(mockList),
                onSportClick = {}
            )
        }

        composeTestRule.onNodeWithText("Soccer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Basketball").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayErrorState() {
        composeTestRule.setContent {
            SportsListScreen(
                uiState = SportsListUiState.Error(AppError.NoConnection),
                onSportClick = {}
            )
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedText = context.getString(br.leandro.thesportsapp.R.string.error_no_connection)
        composeTestRule.onNodeWithText(expectedText, substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun shouldInvokeOnSportClick() {
        var clicked = false
        val mockList = listOf(
            Sport(id = "1", name = "Soccer", image = "", icon = "", description = "")
        )

        composeTestRule.setContent {
            SportsListScreen(
                uiState = SportsListUiState.Success(mockList),
                onSportClick = { clicked = true }
            )
        }

        composeTestRule.onNodeWithText("Soccer").performClick()
        assertTrue(clicked)
    }

}
