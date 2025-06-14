package com.example.gamehub.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import androidx.paging.LoadType
import androidx.paging.PagingState
import com.example.gamehub.data.local.AppDatabase
import com.example.gamehub.data.local.GameEntity
import com.example.gamehub.data.local.RemoteKeysEntity
import com.example.gamehub.data.remote.RawgApi
import com.example.gamehub.data.remote.GameDto
import androidx.room.withTransaction
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewReleasesRemoteMediator(
    private val api: RawgApi,
    private val db: AppDatabase,
    private val apiKey: String
) : RemoteMediator<Int, GameEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GameEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                lastItem?.let { last ->
                    db.remoteKeysDao().remoteKeys(last.id)?.nextKey
                } ?: 1
            }
        } ?: 1

        return try {
            val response = api.getNewReleases(page = page, apiKey = apiKey)
            val games = response.body()?.results ?: emptyList()

            db.withTransaction {
                val entities = games.map { it.toEntity() }
                val keys = games.map {
                    RemoteKeysEntity(
                        gameId = it.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = page + 1
                    )
                }
                db.gameDao().insertAll(entities)
                db.remoteKeysDao().insertAll(keys)
            }

            MediatorResult.Success(endOfPaginationReached = games.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}

private fun GameDto.toEntity(): GameEntity =
    GameEntity(id, name, backgroundImage, rating, released, System.currentTimeMillis())
