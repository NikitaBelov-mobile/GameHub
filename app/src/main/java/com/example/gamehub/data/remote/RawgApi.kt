package com.example.gamehub.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API interface for RAWG service.
 */
interface RawgApi {
    /**
     * Fetches the list of new game releases ordered by release date.
     */
    @GET("games")
    suspend fun getNewReleases(
        @Query("ordering") ordering: String = "-released",
        @Query("key") apiKey: String
    ): Response<GamesResponse>
}
