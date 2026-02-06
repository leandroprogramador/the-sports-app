package br.leandro.core.network.util

import br.leandro.core.network.api.TheSportsDbApi
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider {
    fun getJsonString(jsonFile : String): String {
        return try {
            val inputStream = javaClass.classLoader?.getResourceAsStream(jsonFile)
            inputStream?.bufferedReader().use { it?.readText() } ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    fun createRetrofitServer(server : MockWebServer) = Retrofit.Builder()
        .baseUrl(server.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TheSportsDbApi::class.java)
}