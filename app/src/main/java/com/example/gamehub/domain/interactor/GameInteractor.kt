package com.example.gamehub.domain.interactor

import androidx.paging.PagingData
import com.example.gamehub.domain.model.Game
import com.example.gamehub.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

interface GameInteractor {
    fun newReleases(): Flow<PagingData<Game>>
    fun topRated(): Flow<PagingData<Game>>
}

class GameInteractorImpl(
    private val repository: GameRepository
) : GameInteractor {
    override fun newReleases(): Flow<PagingData<Game>> = repository.newReleases()
    override fun topRated(): Flow<PagingData<Game>> = repository.topRated()
}
