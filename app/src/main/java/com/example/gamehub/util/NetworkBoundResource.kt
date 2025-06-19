package com.example.gamehub.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.emitAll

suspend fun <T> networkBoundResource(
    query: () -> Flow<T>,
    fetch: suspend () -> T,
    saveFetchResult: suspend (T) -> Unit,
    shouldFetch: (T) -> Boolean = { true },
): Flow<T> = flow {
    val data = query().first()
    if (shouldFetch(data)) {
        try {
            saveFetchResult(fetch())
        } catch (t: Throwable) {
            emit(data)
            throw t
        }
    }
    emitAll(query())
}
