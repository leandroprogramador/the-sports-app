package br.leandro.core.network

import br.leandro.core.network.api.TheSportsDbApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.experimental.theories.suppliers.TestedOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TheSportsDbApiTest {
    private lateinit var server : MockWebServer
    private lateinit var api : TheSportsDbApi

    @Before
    fun setup() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheSportsDbApi::class.java)

    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getSports should return sports list when response is successful`() = runTest{
        val json = getJsonString()

        server.enqueue(MockResponse().setResponseCode(200).setBody(json))
        val response = api.getSports()
        assertEquals(1, response.sports?.size)
        val sport = response.sports?.get(0)

        assertEquals("102", sport?.idSport)
        assertEquals("TeamvsTeam", sport?.strFormat)
        assertEquals("Soccer", sport?.strSport)
        assertEquals("Description", sport?.strSportDescription)
        assertEquals("url_thumb", sport?.strSportThumb)
        assertEquals("url_thumb_bw", sport?.strSportThumbBW)
        assertEquals("url_icon", sport?.strSportIconGreen)

        val request = server.takeRequest()
        assertEquals("/all_sports.php", request.path)

    }

    @Test(expected = Exception::class)
    fun `getSports should throw exception when response is not successful`() = runTest {
        server.enqueue(MockResponse().setResponseCode(404))
        api.getSports()

    }

    private fun getJsonString(): String {
        return try {
            val inputStream = javaClass.classLoader?.getResourceAsStream("sports_response.json")
            inputStream?.bufferedReader().use { it?.readText() } ?: ""
        } catch (e: Exception) {
            ""
        }
    }



}