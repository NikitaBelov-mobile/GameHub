package com.example.gamehub.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE gameId = :id")
    suspend fun remoteKeys(id: Int): RemoteKeysEntity?
}
