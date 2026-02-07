package br.leandro.core.domain.usecase

import app.cash.turbine.test
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.League
import br.leandro.core.domain.repository.LeagueRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetLeaguesUseCaseTest {
    private val repository: LeagueRepository = mockk()
    private lateinit var getLeaguesUseCase: GetLeaguesUseCase



    @Before
    fun setup() {
        getLeaguesUseCase = GetLeaguesUseCase(repository)
    }

    @Test
    fun `invoke should call repository and return leagues flow`() = runTest {
        coEvery { repository.getLeagues(country = "Brazil", sport = "Soccer") } returns flowOf(mockLeagues())
        val result = getLeaguesUseCase(country = "Brazil", sport = "Soccer")
        result.test {
            val leaguesList = awaitItem()
            assert(leaguesList.size == mockLeagues().size)
            assert(leaguesList.first().idLeague == mockLeagues().first().idLeague)
            awaitComplete()

        }
    }

    @Test
    fun `invoke should return empty when repository is empty`() = runTest {
        coEvery { repository.getLeagues(country = "Brazil", sport = "Soccer") } returns flowOf(emptyList())
        val result = getLeaguesUseCase(country = "Brazil", sport = "Soccer")
        result.test {
            val leaguesList = awaitItem()
            assert(leaguesList.isEmpty())
            awaitComplete()
        }
    }

    @Test(expected = AppError.ServiceUnavailable::class)
    fun `invoke should return error when repository throws exception`() = runTest {
        coEvery { repository.getLeagues(country = "Brazil", sport = "Soccer") } throws AppError.ServiceUnavailable
        val result = getLeaguesUseCase(country = "Brazil", sport = "Soccer")
        result.test {
            val error = awaitError()
            assert(error is AppError.ServiceUnavailable)
        }

    }

    private fun mockLeagues() = listOf(
        League(
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
    )


}