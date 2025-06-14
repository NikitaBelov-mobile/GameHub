package com.example.gamehub.data.remote

/**
 * Wrapper for a list of games returned by the RAWG API.
 */
data class GamesResponse(
    val results: List<GameDto>
)
