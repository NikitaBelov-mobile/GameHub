package com.example.gamehub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.gamehub.domain.interactor.GameInteractor
import com.example.gamehub.domain.model.Game
import com.example.gamehub.util.NetworkManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    networkManager: NetworkManager,
    interactor: GameInteractor,
) : ViewModel() {
    val isOffline: StateFlow<Boolean> = networkManager.isConnected
        .map { connected -> !connected }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val newReleases: StateFlow<PagingData<Game>> =
        interactor.newReleases()
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val topRated: StateFlow<PagingData<Game>> =
        interactor.topRated()
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun refresh() {}
}
