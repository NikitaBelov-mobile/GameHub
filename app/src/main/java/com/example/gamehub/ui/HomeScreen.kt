package com.example.gamehub.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    /* ---------- state ---------- */

    val context            = LocalContext.current
    val snackbarHostState  = remember { SnackbarHostState() }
    val scope              = rememberCoroutineScope()

    // предполагаем, что во ViewModel есть flow-пара uiState { isRefreshing, isOffline }
    val uiState by viewModel.isOffline.collectAsState()

    // если нужна кастомизация жёстче дефолтной — храните state отдельно
    val pullState = rememberPullToRefreshState()

    /* ---------- side-effects ---------- */

    LaunchedEffect(uiState) {
        if (uiState)  snackbarHostState.showSnackbar("Offline mode")
        else                    snackbarHostState.currentSnackbarData?.dismiss()
    }

    /* ---------- UI ---------- */

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddings ->

        PullToRefreshBox(
            modifier     = Modifier
                .fillMaxSize()
                .padding(paddings),
            state        = pullState,                   // опционально, но полезно для анимаций
            isRefreshing = false,
            onRefresh    = {
                if (uiState) {
                    Toast.makeText(context,
                        "Невозможно обновить без сети",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.refresh()                // запускаем загрузку
                }
            }
        ) {
            /* контент — обязательно скроллируемый */
            Box(Modifier.fillMaxSize()) {
                Text("Home", Modifier.align(Alignment.Center))
            }
        }
    }
}
