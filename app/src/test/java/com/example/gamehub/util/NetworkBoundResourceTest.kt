package com.example.gamehub.util

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NetworkBoundResourceTest {
    @Test
    fun `offline then fetch`() = runTest {
        var saved = false
        val result = networkBoundResource(
            query = { flowOf("db") },
            fetch = { "net" },
            saveFetchResult = { saved = true },
        )
        assertEquals(listOf("db", "net"), result.toList())
        assertEquals(true, saved)
    }
}
