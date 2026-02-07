package br.leandro.core.network

import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.util.NetworkProvider.createRetrofitServer
import br.leandro.core.network.util.NetworkProvider.getJsonString
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test

class LeagueApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: TheSportsDbApi

    @Before
    fun setup() {
        server = MockWebServer()
        api = createRetrofitServer(server)

    }

    @Test
    fun `getLeagues should return leagues list when response is successful`() = runTest {
        val json = getJsonString("leagues_response.json")
        server.enqueue(MockResponse().setResponseCode(200).setBody(json))
        val response = api.getLeagues("Brazil", "Soccer")
        assertEquals(response.countries.size, 2)
        val league = response.countries[0]
        assertEquals("Brazil Serie D", league.strLeague)
        val request = server.takeRequest()
        println("PATH: ${request.path}")
        assertEquals("/search_all_leagues.php?c=Brazil&s=Soccer", request.path)
    }

    @Test(expected = Exception::class)
     fun `getLeagues should throw exception when response is not successful`() = runTest{
        server.enqueue(MockResponse().setResponseCode(404))
        api.getLeagues("Brazil", "Soccer")

    }

}