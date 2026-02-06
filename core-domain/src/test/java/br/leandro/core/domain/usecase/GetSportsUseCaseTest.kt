package br.leandro.core.domain.usecase

import app.cash.turbine.test
import br.leandro.core.domain.model.AppError
import br.leandro.core.domain.model.Sport
import br.leandro.core.domain.repository.SportRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSportsUseCaseTest {
    private val repository: SportRepository = mockk()
    private lateinit var getSportsUseCase : GetSportsUseCase
    private val mockSports = listOf(
        Sport( id = "1",  name = "Soccer", description = "", icon = "", image = ""),
        Sport( id = "1",  name = "Basketball", description = "", icon = "", image = ""),
    )

    @Before
    fun setup() {
        getSportsUseCase = GetSportsUseCase(repository)
    }
    @Test
    fun `invoke should call repository and return sports flow`() = runTest {


        coEvery { repository.getSports() } returns flowOf(mockSports)

        val result = getSportsUseCase()

        result.test {
            val sportsList = awaitItem()
            assert(sportsList.size == mockSports.size)
            assertEquals(mockSports[0].name, sportsList[0].name)
            assertEquals(mockSports[1].name, sportsList[1].name)
            awaitComplete()
        }
    }

    @Test
    fun `invoke should return empty when repository is empty`() = runTest {
        coEvery { repository.getSports() } returns flowOf(emptyList())
        val result = getSportsUseCase()

        result.test {
            val sportsList = awaitItem()
            assert(sportsList.isEmpty())
            awaitComplete()
        }

    }

    @Test(expected = AppError.Unknown::class)
    fun `invoke should return error when repository throws exception`() = runTest {
        coEvery { repository.getSports() } throws AppError.Unknown
        val result = getSportsUseCase()

        result.test {
            val error = awaitError()
            assertEquals(AppError.Unknown, error)
        }
    }


}



