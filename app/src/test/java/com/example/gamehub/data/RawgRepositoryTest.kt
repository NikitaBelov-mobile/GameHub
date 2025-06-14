package com.example.gamehub.data

import com.example.gamehub.data.remote.GamesResponse
import com.example.gamehub.data.remote.RawgApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
//import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.assertEquals

private class RawgRepository(private val api: RawgApi) {
    suspend fun getNewReleases(apiKey: String) = api.getNewReleases(apiKey = apiKey)
}

class RawgRepositoryTest {
    private val api: RawgApi = mock()
    private val repository = RawgRepository(api)

    @Test
    fun `repository delegates to api`() = runTest {
        val expected = Response.success(GamesResponse(emptyList()))
        doReturn(expected).whenever(api).getNewReleases(any(), eq("key"))

        val result = repository.getNewReleases("key")

        verify(api).getNewReleases(eq("-released"), eq("key"))
        assertEquals(expected, result)
    }
}
