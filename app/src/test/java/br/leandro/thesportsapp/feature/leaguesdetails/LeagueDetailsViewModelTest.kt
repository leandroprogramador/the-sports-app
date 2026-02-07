package br.leandro.thesportsapp.feature.leaguesdetails


import br.leandro.core.domain.model.League
import br.leandro.thesportsapp.feature.leaguedetails.LeagueDetailsUiState
import br.leandro.thesportsapp.feature.leaguedetails.LeagueDetailsViewModel
import br.leandro.thesportsapp.feature.leaguesdetails.LeagueDetailsViewModelTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class LeagueDetailsViewModelTest {
    private lateinit var viewModelTest: LeagueDetailsViewModel
    val league = League(
        idLeague = "1234",
        division = 1,
        formedYear = "1959",
        badge = "https://upload.wikimedia.org/wikipedia/pt/0/0e/Campeonato_Brasileiro_Série_A_logo.png",
        complete = true,
        country = "Brazil",
        currentSeason = "2026",
        descriptionEN = "The Campeonato Brasileiro Série A is the top tier of professional football in Brazil.",
        descriptionPT = "O Campeonato Brasileiro Série A é a principal divisão do futebol profissional no Brasil.",
        image = "",
        gender = "Male",
        league = "Campeonato Brasileiro",
        leagueAlternate = "Brasileirão",
        sport = "Soccer",
        tvRights = "Globo, CazéTV",
        isCup = false
    )

    @Before
    fun setup() {
        viewModelTest = LeagueDetailsViewModel()
    }

    @Test
    fun `when viewmodel is created then state should be Loading`() {
        assertEquals(
            LeagueDetailsUiState.Loading,
            viewModelTest.uiState.value
        )
    }

    @Test
    fun `when initLeagueDetails is called, it should update uiState with the provided sport`() =
        runTest {
            viewModelTest.initLeagueDetails(league)
            val uiState = viewModelTest.uiState.value
            assert(uiState is LeagueDetailsUiState.Success)
            assertEquals(league, (uiState as LeagueDetailsUiState.Success).league)

        }
}