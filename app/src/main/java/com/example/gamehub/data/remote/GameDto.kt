package com.example.gamehub.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data transfer object representing a game from RAWG API.
 */
@Serializable
data class GameDto(
    val id: Int,
    val name: String,
    @SerialName("background_image")
    val backgroundImage: String? = null,
    val rating: Double? = null,
)
