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

    /**
     * Fetches the list of top rated games ordered by rating.
     */
    @GET("games")
    suspend fun getTopRated(
        @Query("ordering") ordering: String = "-rating",
        @Query("key") apiKey: String
    ): Response<GamesResponse>
}
