package com.example.gamehub.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isOffline by viewModel.isOffline.collectAsState()

    val refreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            if (isOffline) {
                Toast.makeText(context, "Невозможно обновить без сети", Toast.LENGTH_SHORT).show()
            } else {
                scope.launch {
                    // trigger refresh here
                }
            }
        }
    )

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar("Offline mode")
        } else {
            snackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(refreshState)
        ) {
            // Placeholder content
            Text("Home", Modifier.align(Alignment.Center))
            PullRefreshIndicator(false, refreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}
