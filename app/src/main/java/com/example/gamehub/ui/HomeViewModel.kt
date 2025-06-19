package com.example.gamehub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamehub.util.NetworkManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    networkManager: NetworkManager,
) : ViewModel() {
    val isOffline: StateFlow<Boolean> = networkManager.isConnected
        .map { connected -> !connected }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
}
