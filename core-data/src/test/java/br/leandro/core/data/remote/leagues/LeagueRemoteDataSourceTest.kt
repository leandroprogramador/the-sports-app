package br.leandro.core.data.remote.leagues

import br.leandro.core.data.remote.league.LeaguesRemoteDataSource
import br.leandro.core.data.remote.league.LeaguesRemoteDataSourceImpl
import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.model.dto.LeagueDto
import br.leandro.core.network.model.dto.LeagueResponseDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class LeagueRemoteDataSourceTest {
    private val api: TheSportsDbApi = mockk()
    private lateinit var dataSource: LeaguesRemoteDataSource
    private val dtoLeagueResponse = LeagueResponseDto(
        countries = listOf(
            LeagueDto(
                idLeague = "1234",
                intDivision = "1",
                intFormedYear = "1959",
                strBadge = "https://upload.wikimedia.org/wikipedia/pt/0/0e/Campeonato_Brasileiro_Série_A_logo.png",
                strComplete = "Campeonato Brasileiro Série A",
                strCountry = "Brazil",
                strCurrentSeason = "2026",
                strDescriptionEN = "The Campeonato Brasileiro Série A is the top tier of professional football in Brazil.",
                strDescriptionPT = "O Campeonato Brasileiro Série A é a principal divisão do futebol profissional no Brasil.",
                strFanart1 = null,
                strGender = "Male",
                strLeague = "Campeonato Brasileiro",
                strLeagueAlternate = "Brasileirão",
                strPoster = "",
                strSport = "Soccer",
                strTvRights = "Globo, CazéTV"
            )
        )
    )

    @Before
    fun setup() {
        dataSource = LeaguesRemoteDataSourceImpl(api)
    }

    @Test
    fun `when api answer success should return leagues list`() = runTest {
        coEvery { api.getLeagues(country = "Brazil", sport = "Soccer") } returns dtoLeagueResponse
        val result = dataSource.getLeagues(country = "Brazil", sport = "Soccer")
        assert(result.size == dtoLeagueResponse.countries.size)
        assert(result.first().strLeague == dtoLeagueResponse.countries.first().strLeague)

    }

    @Test(expected = IOException::class)
    fun `when api fails should return exception`() = runTest {
        coEvery { api.getLeagues(country = "Brazil", sport = "Soccer") } throws IOException()
        dataSource.getLeagues(country = "Brazil", sport = "Soccer")

    }

}



