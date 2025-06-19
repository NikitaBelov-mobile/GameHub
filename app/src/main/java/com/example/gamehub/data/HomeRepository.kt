package com.example.gamehub.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.gamehub.BuildConfig
import com.example.gamehub.data.local.AppDatabase
import com.example.gamehub.data.local.GameDao
import com.example.gamehub.data.remote.RawgApi
import com.example.gamehub.domain.Game
import com.example.gamehub.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepository(
    private val api: RawgApi,
    private val db: AppDatabase,
    private val gameDao: GameDao = db.gameDao(),
    private val apiKey: String = BuildConfig.RAWG_KEY,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun newReleases(): Flow<PagingData<Game>> {
        val mediator = NewReleasesRemoteMediator(api, db, apiKey)
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = mediator,
            pagingSourceFactory = { gameDao.pagingNew() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun topRated(): Flow<PagingData<Game>> {
        val mediator = TopRatedRemoteMediator(api, db, apiKey)
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = mediator,
            pagingSourceFactory = { gameDao.pagingNew() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}
