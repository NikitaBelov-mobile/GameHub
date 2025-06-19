package com.example.gamehub.domain.model

data class Game(
    val id: Int,
    val name: String,
    val backgroundImage: String?,
    val rating: Double?,
    val released: String?,
)
