package com.example.gamehub.data.remote

import com.google.gson.annotations.SerializedName

/**
 * Data transfer object representing a game from RAWG API.
 */
data class GameDto(
    val id: Int,
    val name: String,
    @SerializedName("released")
    val releaseDate: String?,
    @SerializedName("background_image")
    val backgroundImage: String?
)
