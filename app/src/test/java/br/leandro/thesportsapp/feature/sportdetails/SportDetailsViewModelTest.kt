package br.leandro.thesportsapp.feature.sportdetails

import br.leandro.core.domain.model.Sport
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class SportDetailsViewModelTest {
    private lateinit var sportDetailsViewModel : SportDetailsViewModel

    @Before
    fun setup(){
        sportDetailsViewModel = SportDetailsViewModel()
    }

    @Test
    fun `when viewmodel is created then state should be Loading`() {
        assertEquals(
            SportDetailsUiState.Loading,
            sportDetailsViewModel.uiState.value
        )
    }
    @Test
    fun `when initSportDetails is called, it should update uiState with the provided sport`() =
        runTest {
            val sport = Sport(id = "1", name = "Soccer", description = "Soccer description", icon = "", image = "")
            sportDetailsViewModel.initSportDetails(sport)
            val uiState = sportDetailsViewModel.uiState.value
            assert(uiState is SportDetailsUiState.Success)
            assertEquals(sport, (uiState as SportDetailsUiState.Success).sport)

        }
}