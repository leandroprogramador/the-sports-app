package br.leandro.core.data.repository

import app.cash.turbine.test
import br.leandro.core.data.local.datasource.sports.SportsLocalDataSource
import br.leandro.core.data.local.entity.SportEntity
import br.leandro.core.data.remote.sports.SportsRemoteDataSource
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.repository.SportRepository
import br.leandro.core.network.model.dto.SportsDto
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class SportRepositoryImplTest {
    private val remoteDataSource : SportsRemoteDataSource = mockk()
    private val localDataSource : SportsLocalDataSource = mockk()
    private lateinit var repository : SportRepository

    @Before
    fun setup() {
        repository = SportRepositoryImpl(remoteDataSource, localDataSource)
    }


    @Test
    fun `when api answer success should update local list and return sports list`() = runTest {
        coEvery { localDataSource.getSports() } returns flowOf(sportEntity())
        coEvery { remoteDataSource.getSports() } returns sportsDto()

        coEvery { localDataSource.saveSports(any()) } just Runs
        coEvery { localDataSource.hasData() } returns true

        val result = repository.getSports()
        result.test {
            assertEquals(sportEntity().first().id, awaitItem().first().id)
            awaitComplete()
        }

    }

    @Test
    fun `when api fails and has local should return local sports list`() = runTest {
        coEvery { localDataSource.getSports() } returns flowOf(sportEntity())
        coEvery { remoteDataSource.getSports() } throws AppError.ServiceUnavailable
        coEvery { localDataSource.hasData() } returns true

        val result = repository.getSports()
        result.test {
            assertEquals(sportEntity().first().id, awaitItem().first().id)
            awaitComplete()
        }

    }

    @Test(expected = AppError.NoConnection::class)
    fun `when api fails and has no local should throw exception`() = runTest {
        coEvery { localDataSource.getSports() } returns flowOf(emptyList())
        coEvery { localDataSource.hasData() } returns false
        coEvery { remoteDataSource.getSports() } throws UnknownHostException()
        repository.getSports().collect()

    }


    private fun sportEntity() = listOf(
        SportEntity(
            id = "1",
            name = "Soccer",
            description = "Soccer description",
            iconUrl = "",
            imageUrl = ""
        )
    )

    private fun sportsDto() = listOf(
        SportsDto(
            idSport = "1",
            strFormat = "TeamvsTeam",
            strSport = "Soccer",
            strSportDescription = "Soccer description",
            strSportThumb = "",
            strSportThumbBW = "",
            strSportIconGreen = ""
        )

    )
}