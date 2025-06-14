package com.example.gamehub.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
//import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

import org.junit.Assert.assertEquals

class RawgApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: RawgApi

    @Before
    fun setup() {
        server = MockWebServer()
        val contentType = "application/json".toMediaType()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .build()
            .create(RawgApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getNewReleases returns 200`() {
        val mockJson = """{ "results": [] }"""
        server.enqueue(MockResponse().setResponseCode(200).setBody(mockJson))

        runBlocking {
            val response = api.getNewReleases(apiKey = "test")
            val request = server.takeRequest()
            assertEquals("/games?ordering=-released&key=test", request.path)
            assertEquals(1, server.requestCount)
            assertEquals(200, response.code())
            // Assert that response parsed correctly
            assertEquals(0, response.body()!!.results.size)
        }
    }

    @Test
    fun `getTopRated returns 200`() {
        val mockJson = """{ "results": [] }"""
        server.enqueue(MockResponse().setResponseCode(200).setBody(mockJson))

        runBlocking {
            val response = api.getTopRated(apiKey = "test")
            val request = server.takeRequest()
            assertEquals("/games?ordering=-rating&key=test", request.path)
            assertEquals(1, server.requestCount)
            assertEquals(200, response.code())
            assertEquals(0, response.body()!!.results.size)
        }
    }
}
