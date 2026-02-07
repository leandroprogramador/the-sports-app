package br.leandro.core.data.repository

import app.cash.turbine.test
import br.leandro.core.data.local.datasource.leagues.LeaguesLocalDataSource
import br.leandro.core.data.local.entity.LeagueEntity
import br.leandro.core.data.mapper.toDomain
import br.leandro.core.data.mapper.toEntity
import br.leandro.core.data.remote.league.LeaguesRemoteDataSource
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.repository.LeagueRepository
import br.leandro.core.network.model.dto.LeagueDto
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class LeagueRepositoryImplTest {
    private val remoteDataSource: LeaguesRemoteDataSource = mockk()
    private val localDataSource: LeaguesLocalDataSource = mockk()
    private lateinit var repository: LeagueRepository

    @Before
    fun setup() {
        repository = LeagueRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `when api answer success should update local list and return leagues list`() = runTest {
        coEvery { localDataSource.getLeagues(country = "Brazil", sport = "Soccer")} returns flowOf(leaguesEntity())
        coEvery { remoteDataSource.getLeagues(country = "Brazil", sport = "Soccer")} returns leaguesDto()
        coEvery { localDataSource.saveLeagues(leaguesDto().map { it.toDomain().toEntity() }) } returns Unit
        coEvery { localDataSource.hasData() } returns true

        val result = repository.getLeagues(country = "Brazil", sport = "Soccer")
        result.test {
            assertEquals(leaguesEntity().first().idLeague, awaitItem().first().idLeague)
            awaitComplete()
        }
    }

    @Test
    fun `when api fails and has local should return local leagues list`() = runTest {
        coEvery { localDataSource.getLeagues(country = "Brazil", sport = "Soccer")} returns flowOf(leaguesEntity())
        coEvery { remoteDataSource.getLeagues(country = "Brazil", sport = "Soccer")} throws AppError.ServiceUnavailable
        coEvery { localDataSource.hasData() } returns true

        val result = repository.getLeagues(country = "Brazil", sport = "Soccer")
        result.test {
            assertEquals(leaguesEntity().first().idLeague, awaitItem().first().idLeague)
            awaitComplete()
        }

    }

    @Test(expected = AppError.NoConnection::class)
    fun `when api fails and has no local should throw exception`() = runTest {
        coEvery { localDataSource.getLeagues(country = "Brazil", sport = "Soccer")} returns flowOf(emptyList())
        coEvery { localDataSource.hasData() } returns false
        coEvery { remoteDataSource.getLeagues(country = "Brazil", sport = "Soccer")} throws UnknownHostException()
        repository.getLeagues(country = "Brazil", sport = "Soccer").collect()

    }





    private fun leaguesEntity() = listOf(
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


    private fun leaguesDto() =  listOf(
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
}