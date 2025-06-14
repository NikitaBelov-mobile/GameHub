package com.example.gamehub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey val gameId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
)
