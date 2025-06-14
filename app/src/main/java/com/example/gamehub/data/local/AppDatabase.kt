package com.example.gamehub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GameEntity::class, RemoteKeysEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
