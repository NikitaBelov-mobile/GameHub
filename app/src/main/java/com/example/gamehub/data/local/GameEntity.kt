package com.example.gamehub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val backgroundImage: String?,
    val rating: Double?,
    val released: String?,
    val lastUpdated: Long,
)
