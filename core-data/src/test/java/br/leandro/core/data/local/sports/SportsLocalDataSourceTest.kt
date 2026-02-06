package br.leandro.core.data.local.sports

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

    @Before
    fun setup() {
        dataSource = SportsLocalDataSourceImpl(dao)

    }

    @Test
    fun `when getSports is called should return sports list`() = runTest {
        val entitySportsList = listOf(
            SportEntity(
                id = "1",
                name = "Soccer",
                description = "Soccer description",
                iconUrl = "",
                imageUrl = ""
            )
        )
        every { dao.getSports() } returns flowOf(entitySportsList)
        val flow = dataSource.getSports()
        val result = flow.toList()
        assertEquals(entitySportsList.size, result.size)
        assertEquals(entitySportsList.first().id, result.first().first().id)


    }

    @Test
    fun `has data should return true when dao has data`() = runTest {
        coEvery { dao.count() } returns 1
        val result = dataSource.hasData()
        assertEquals(true, result)

    }

    @Test
    fun `when insert is called and count is greater than zero then hasData returns true`() = runTest {
        val sports = listOf(
            SportEntity(
                id = "1",
                name = "Soccer",
                description = "desc",
                iconUrl = "",
                imageUrl = ""
            )
        )

        coEvery { dao.insertSports(sports) } just Runs
        coEvery { dao.count() } returns 1

        dataSource.saveSports(sports)
        val result = dataSource.hasData()
        assertEquals(true, result)

        coVerify(exactly = 1) { dao.insertSports(sports) }
        coVerify(exactly = 1) { dao.count() }

    }


}