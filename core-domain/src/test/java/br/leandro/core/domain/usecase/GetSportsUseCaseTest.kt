package br.leandro.core.domain.usecase

import app.cash.turbine.test
import br.leandro.core.domain.model.Sport
import br.leandro.core.domain.repository.SportRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetSportsUseCaseTest {
    private val repository: SportRepository = mockk()
    private val getSportsUseCase = GetSportsUseCase(repository)

    @Test
    fun `invoke should call repository and return sports flow`() = runTest {
        val mockSports = listOf(
            Sport("1", "Soccer", "", icon = ""),
            Sport("2", "Basketball", "", icon = "")
        )

        coEvery { repository.getSports() } returns flowOf(mockSports)

        val result = getSportsUseCase()

        result.test {
            val sportsList = awaitItem()
            assert(sportsList.size == 2)
            assertEquals("Soccer", sportsList[0].name)
            assertEquals("Basketball", sportsList[1].name)
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


}



