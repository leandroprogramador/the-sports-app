package br.leandro.core.data.repository

import app.cash.turbine.test
import br.leandro.core.data.datasource.SportsDataSource
import br.leandro.core.domain.model.AppError
import br.leandro.core.network.model.dto.SportsDto
import br.leandro.core.network.model.dto.SportsResponseDto
import io.mockk.coEvery
import kotlinx.coroutines.test.runTest

import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SportRepositoryImplTest {
    private val dataSource : SportsDataSource = mockk()
    private val repository = SportRepositoryImpl(dataSource)

    @Test
    fun `getSports should call data source and map to domain models`() = runTest {
        val mockDto = SportsDto(
            idSport = "102",
            strFormat = "TeamvsTeam",
            strSport = "Soccer",
            strSportThumb = "thumb_url",
            strSportThumbBW = "thumb_url",
            strSportIconGreen = "icon_url",
            strSportDescription = "Description",
        )

        val mockResponse = SportsResponseDto(sports = listOf(mockDto))
        coEvery { dataSource.getSports() } returns mockResponse

        val result = repository.getSports()

        result.test {
            val sportsList = awaitItem()
            assertEquals(1, sportsList.size)
            val sportItem = sportsList.first()
            assertEquals("102", sportItem.id)
            assertEquals("Soccer", sportItem.name)

            awaitComplete()
        }

    }

    @Test
    fun `getSports should return empty list when data source returns null`() = runTest {
        val mockResponse = SportsResponseDto(sports = null)
        coEvery { dataSource.getSports() } returns mockResponse
        val result = repository.getSports()

        result.test {
            val sportsList = awaitItem()
            assertEquals(0, sportsList.size)
            awaitComplete()
            }


    }

    @Test
    fun `getSports should throw exception when data source fails`() = runTest {
        coEvery { dataSource.getSports() } throws AppError.Unknown

        repository.getSports().test {
            val error = awaitError()
            assertEquals(AppError.Unknown.message, error.message)
        }
    }
}