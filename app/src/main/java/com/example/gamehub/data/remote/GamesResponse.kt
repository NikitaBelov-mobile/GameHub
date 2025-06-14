package com.example.gamehub.data.remote

import kotlinx.serialization.Serializable

/**
 * Wrapper for a list of games returned by the RAWG API.
 */
@Serializable
data class GamesResponse(
    val results: List<GameDto>
)
