package br.leandro.thesportsapp.feature.sportslist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.leandro.core.domain.model.Sport
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

        composeTestRule.onNodeWithText("Carregando", substring = true)
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

        // No wait needed - data is passed directly
        composeTestRule.onNodeWithText("Soccer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Basketball").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayErrorState() {
        composeTestRule.setContent {
            SportsListScreen(
                uiState = SportsListUiState.Error("Erro de conexão"),
                onSportClick = {}
            )
        }

        composeTestRule.onNodeWithText("Erro de conexão", substring = true)
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
