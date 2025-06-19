package com.example.gamehub.domain.repository

import androidx.paging.PagingData
import com.example.gamehub.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun newReleases(): Flow<PagingData<Game>>
    fun topRated(): Flow<PagingData<Game>>
}
