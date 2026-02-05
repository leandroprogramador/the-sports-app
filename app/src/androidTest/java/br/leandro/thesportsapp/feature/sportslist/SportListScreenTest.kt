package br.leandro.thesportsapp.feature.sportslist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import br.leandro.core.domain.model.Sport
import org.junit.Rule
import org.junit.Test

class SportsListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayLoadingState() {
        // Directly test the UI state - no Koin, no mocks needed
        composeTestRule.setContent {
            SportsListScreen(
                uiState = SportsListUiState.Loading,
                onSportClick = {}
            )
        }

        // No wait needed - loading should be immediate
        composeTestRule.onNodeWithText("Carregando", substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun shouldDisplaySuccessState() {
        val mockList = listOf(
            Sport(id = "1", name = "Soccer", image = "", icon = ""),
            Sport(id = "2", name = "Basketball", image = "", icon = "")
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

        // No wait needed - error state is immediate
        composeTestRule.onNodeWithText("Erro de conexão", substring = true)
            .assertIsDisplayed()
    }
}