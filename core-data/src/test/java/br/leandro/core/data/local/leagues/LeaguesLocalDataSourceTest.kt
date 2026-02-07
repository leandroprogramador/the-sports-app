package br.leandro.core.data.local.leagues

import app.cash.turbine.test
import br.leandro.core.data.local.dao.LeagueDao
import br.leandro.core.data.local.datasource.leagues.LeaguesLocalDataSourceImpl
import br.leandro.core.data.local.entity.LeagueEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LeaguesLocalDataSourceTest {
    private val dao: LeagueDao = mockk(relaxed = true)
    private lateinit var dataSource: LeaguesLocalDataSourceImpl
    private val entityLeaguesList = listOf(
        LeagueEntity(
            idLeague = "1234",
            division = "1",
            formedYear = "1959",
            badge = "https://upload.wikimedia.org/wikipedia/pt/0/0e/Campeonato_Brasileiro_Série_A_logo.png",
            complete = "Campeonato Brasileiro Série A",
            country = "Brazil",
            currentSeason = "2026",
            descriptionEN = "The Campeonato Brasileiro Série A is the top tier of professional football in Brazil.",
            descriptionPT = "O Campeonato Brasileiro Série A é a principal divisão do futebol profissional no Brasil.",
            image = "",
            gender = "Male",
            league = "Campeonato Brasileiro",
            leagueAlternate = "Brasileirão",
            sport = "Soccer",
            tvRights = "Globo, CazéTV"
        )

    )


    @Before
    fun setup() {
        dataSource = LeaguesLocalDataSourceImpl(dao)
    }

    @Test
    fun `when getLeagues is called should return leagues list`() = runTest {
        every { dao.getLeagues(sport = "Soccer", country = "Brazil") } returns flowOf(entityLeaguesList)
        val result = dataSource.getLeagues(sport = "Soccer", country = "Brazil")
        result.test {
            assertEquals(entityLeaguesList, awaitItem())
            awaitComplete()
        }

    }

    @Test
    fun `has data should return true when dao has data`() = runTest {
        coEvery { dao.count() } returns 1
        val result = dataSource.hasData()
        assertEquals(true, result)
    }

    @Test
    fun `when insert is called and count is greater than zero then hasData returns true`() = runTest {
        coEvery { dao.insertLeagues(leagues = entityLeaguesList) } just Runs
        coEvery { dao.count() } returns entityLeaguesList.size

        dataSource.saveLeagues(entityLeaguesList)
        val result = dataSource.hasData()
        assertEquals(true, result)

        coVerify(exactly = 1) { dao.insertLeagues(leagues = entityLeaguesList) }
        coVerify(exactly = 1) { dao.count() }

    }



}


