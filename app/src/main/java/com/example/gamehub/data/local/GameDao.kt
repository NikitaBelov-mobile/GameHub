package com.example.gamehub.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {
    @Query("SELECT * FROM games ORDER BY released DESC")
    fun pagingNew(): PagingSource<Int, GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(games: List<GameEntity>)

    @Query("DELETE FROM games")
    suspend fun clearAll()

    @Query("DELETE FROM games WHERE lastUpdated < :time")
    suspend fun deleteOlderThan(time: Long)
}
