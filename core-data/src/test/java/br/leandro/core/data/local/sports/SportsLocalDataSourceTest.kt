package br.leandro.core.data.local.sports

import app.cash.turbine.test
import br.leandro.core.data.local.dao.SportDao
import br.leandro.core.data.local.datasource.sports.SportsLocalDataSourceImpl
import br.leandro.core.data.local.entity.SportEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SportsLocalDataSourceTest {
    private val dao: SportDao = mockk(relaxed = true)
    private lateinit var dataSource: SportsLocalDataSourceImpl
    private val entitySportsList = listOf(
        SportEntity(
            id = "1",
            name = "Soccer",
            description = "Soccer description",
            iconUrl = "",
            imageUrl = ""
        )
    )

    @Before
    fun setup() {
        dataSource = SportsLocalDataSourceImpl(dao)

    }

    @Test
    fun `when getSports is called should return sports list`() = runTest {

        every { dao.getSports() } returns flowOf(entitySportsList)
        val result = dataSource.getSports()
        result.test {
            assertEquals(entitySportsList, awaitItem())
            awaitComplete()

        }


    }

    @Test
    fun `has data should return true when dao has data`() = runTest {
        coEvery { dao.count() } returns entitySportsList.size
        val result = dataSource.hasData()
        assertEquals(true, result)

    }

    @Test
    fun `when insert is called and count is greater than zero then hasData returns true`() = runTest {

        coEvery { dao.insertSports(entitySportsList) } just Runs
        coEvery { dao.count() } returns entitySportsList.size

        dataSource.saveSports(entitySportsList)
        val result = dataSource.hasData()
        assertEquals(true, result)

        coVerify(exactly = 1) { dao.insertSports(entitySportsList) }
        coVerify(exactly = 1) { dao.count() }

    }


}