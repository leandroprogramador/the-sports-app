package br.leandro.core.data.remote.sports

import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.model.dto.SportsDto
import br.leandro.core.network.model.dto.SportsResponseDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class SportsRemoteDataSourceTest {
    private val api : TheSportsDbApi = mockk()
    private lateinit var dataSource: SportsRemoteDataSourceImpl

    @Before
    fun setup() {
        dataSource = SportsRemoteDataSourceImpl(api)

    }

    @Test
    fun `when api answer success should return sports list`() = runTest {
        val dtoResponse = SportsResponseDto(listOf(SportsDto(
            idSport = "102",
            strFormat = "TeamvsTeam",
            strSport = "Soccer",
            strSportThumb = "thumb_url",
            strSportThumbBW = "thumb_url",
            strSportIconGreen = "icon_url",
            strSportDescription = "Description",
        )))

        coEvery { api.getSports() } returns dtoResponse
        val result = dataSource.getSports()
        assert(result.size == 1)
        assert(result.first().idSport == "102")


    }

    @Test(expected = IOException::class)
    fun `when api fails should return exception`() = runTest {
        coEvery { api.getSports() } throws IOException()
        dataSource.getSports()



    }
}