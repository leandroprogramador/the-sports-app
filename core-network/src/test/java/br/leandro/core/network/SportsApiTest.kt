package br.leandro.core.network

import br.leandro.core.network.api.TheSportsDbApi
import br.leandro.core.network.util.NetworkProvider.createRetrofitServer
import br.leandro.core.network.util.NetworkProvider.getJsonString
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SportsApiTest {
    private lateinit var server : MockWebServer
    private lateinit var api : TheSportsDbApi

    @Before
    fun setup() {
        server = MockWebServer()
        api = createRetrofitServer(server)

    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getSports should return sports list when response is successful`() = runTest{
        val json = getJsonString("sports_response.json")

        server.enqueue(MockResponse().setResponseCode(200).setBody(json))
        val response = api.getSports()
        assertEquals(1, response.sports?.size)
        val sport = response.sports?.get(0)

        assertEquals("102", sport?.idSport)
        assertEquals("Soccer", sport?.strSport)

        val request = server.takeRequest()
        assertEquals("/all_sports.php", request.path)

    }

    @Test(expected = Exception::class)
    fun `getSports should throw exception when response is not successful`() = runTest {
        server.enqueue(MockResponse().setResponseCode(404))
        api.getSports()

    }





}